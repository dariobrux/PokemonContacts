package com.dariobrux.pokemon.app.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dariobrux.pokemon.app.data.models.DataInfo
import com.dariobrux.pokemon.app.data.models.Pokemon
import com.dariobrux.pokemon.app.other.Resource

/**
 *
 * Created by Dario Bruzzese on 22/10/2020.
 *
 */
class MainViewModel @ViewModelInject constructor(private val mainRepository: MainRepository) : ViewModel() {

    val pokemonList = mutableListOf<Pokemon>()

    /**
     * The Adapter to show the items in list.
     */
    var adapter: MainAdapter? = null

    /**
     * @return the pokemon list.
     */
    fun getPokemon(): LiveData<Resource<DataInfo>>? {
        return mainRepository.getPokemon()
    }

    /**
     * Refresh the pokemon list, reloading from the first pokemon.
     */
    fun refreshPokemon(): LiveData<Resource<DataInfo>>? {
        resetOffset()
        return getPokemon()
    }

    fun resetOffset() {
        mainRepository.resetOffset()
    }
}