package com.dariobrux.pokemon.app.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dariobrux.pokemon.app.R
import com.dariobrux.pokemon.app.data.remote.PokemonApiHelper
import com.dariobrux.pokemon.app.data.local.PokemonDao
import com.dariobrux.pokemon.app.data.models.DataInfo
import com.dariobrux.pokemon.app.other.Constants
import com.dariobrux.pokemon.app.other.Resource
import com.dariobrux.pokemon.app.other.extensions.getIdFromUrl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

/**
 *
 * Created by Dario Bruzzese on 22/10/2020.
 *
 * This class is the repository that handles the communication
 * between the restful api and the database.
 *
 */
class MainRepository @Inject constructor(private val pokemonApiHelper: PokemonApiHelper, private val pokemonDao: PokemonDao) {

    /**
     * Increment it to display the next set of items.
     */
    private var offset = 0

    /**
     * Max number of items to download in once.
     */
    private var limit = 100

    /**
     * Reset the offset to start from the first pokemon.
     */
    fun resetOffset() {
        offset = 0
    }

    /**
     * Get the list of the pokemon from a restful api or from the database.
     * Read first the local pokemon list from db. If this list is not empty,
     * notify that some pokemon are available.
     * If the local list is empty, download the pokemon from the api. Then,
     * store this list in the database.
     * @return the [DataInfo] object mapped into a [Resource], inside a [LiveData].
     */
    fun getPokemon(): LiveData<Resource<DataInfo>> {
        val mutableLiveData: MutableLiveData<Resource<DataInfo>> = MutableLiveData()

        // This runs into a Coroutine Scope
        CoroutineScope(Dispatchers.IO).launch {

            var dataInfo: DataInfo? = null

            // Read first the local pokemon list from database.
            val localPokemonList = pokemonDao.getPokemonList(offset, limit)

            // If it is not empty, read and pass the data retrieved from database.
            if (!localPokemonList.isNullOrEmpty()) {
                Timber.d("Read the pokemon list from the database.")
                dataInfo = DataInfo().apply {
                    pokemonList = localPokemonList
                }
            } else {

                Timber.d("Trying to retrieve the pokemon list from url.")

                // If the database is empty, download the pokemon from the online API and
                // store them in the database.
                kotlin.runCatching {
                    pokemonApiHelper.getPokemon(offset, limit)
                }.onSuccess {
                    dataInfo = if (it.status == Resource.Status.SUCCESS) {
                        it.data
                    } else {
                        null
                    }

                    // Store in the database.
                    dataInfo?.pokemonList?.let { pokemonList ->
                        pokemonList.forEach { pokemon ->
                            pokemon.num = pokemon.url?.getIdFromUrl() ?: -1
                            pokemon.urlPicture = String.format(Constants.BASE_PICTURE_URL, pokemon.num)
                        }
                        Timber.d("Insert the pokemon list in the database.")
                        pokemonDao.insertPokemonList(pokemonList)
                    }
                }.onFailure {
                    Timber.w("Problems while retrieve the pokemon list.")
                }
            }

            // Finish Coroutine and pass on the Main Thread
            withContext(Dispatchers.Main) {
                mutableLiveData.value = Resource(Resource.Status.SUCCESS, dataInfo, null)
                dataInfo?.also {
                    offset += limit
                }
            }
        }

        return mutableLiveData
    }
}