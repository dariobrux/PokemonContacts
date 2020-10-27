package com.dariobrux.pokemon.app.data.remote

import com.dariobrux.pokemon.app.data.models.DataInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *
 * Created by Dario Bruzzese on 22/10/2020.
 *
 * Interface for Retrofit that contains the declaration of the API to invoke.
 *
 */

interface PokemonService {

    /**
     * Get the [DataInfo] with the pokemon list.
     * @param offset it will be retrieved a list starting from this value
     * @param limit maximum number of items to retrieve.
     * @return the [DataInfo] mapped into a [Response] object.
     */
    @GET("api/v2/pokemon")
    suspend fun pokemon(@Query("offset") offset: Int, @Query("limit") limit: Int): Response<DataInfo>
}