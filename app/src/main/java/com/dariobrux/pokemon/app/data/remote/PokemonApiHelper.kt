package com.dariobrux.pokemon.app.data.remote

import com.dariobrux.pokemon.app.data.models.DataInfo
import retrofit2.Response
import javax.inject.Inject

/**
 *
 * Created by Dario Bruzzese on 22/10/2020.
 *
 * This class get the results from the api service and map the result object
 * to an useful object with the status.
 *
 */

class PokemonApiHelper @Inject constructor(private val pokemonService: PokemonService) : ApiHelper() {

    /**
     * Get the [DataInfo] with the pokemon list.
     * @param offset it will be retrieved a list starting from this value
     * @param limit maximum number of items to retrieve.
     * @return the [DataInfo] mapped into a [Response] object.
     */
    suspend fun getPokemon(offset: Int, limit: Int) = getResult { pokemonService.pokemon(offset, limit) }
}