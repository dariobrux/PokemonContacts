package com.dariobrux.pokemon.app.data.remote

import com.dariobrux.pokemon.app.data.models.PokemonData
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

class PokemonDataApiHelper @Inject constructor(private val pokemonDataService: PokemonDataService) : ApiHelper() {

    /**
     * Get the [PokemonData] with the pokemon info
     * @param url the dynamic url to call to get the data.
     * @return the [PokemonData] mapped into a [Response] object.
     */
    suspend fun getPokemonData(url: String) = getResult { pokemonDataService.pokemonData(url) }
}