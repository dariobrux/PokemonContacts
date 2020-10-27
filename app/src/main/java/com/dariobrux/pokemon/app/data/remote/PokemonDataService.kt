package com.dariobrux.pokemon.app.data.remote

import com.dariobrux.pokemon.app.data.models.PokemonData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

/**
 *
 * Created by Dario Bruzzese on 22/10/2020.
 *
 * Interface for Retrofit that contains the declaration of the API to invoke.
 *
 */

interface PokemonDataService {

    /**
     * Get the [PokemonData] with the pokemon info
     * @param url the dynamic url to call to get the data.
     * @return the [PokemonData] mapped into a [Response] object.
     */
    @GET
    suspend fun pokemonData(@Url url: String) : Response<PokemonData>
}