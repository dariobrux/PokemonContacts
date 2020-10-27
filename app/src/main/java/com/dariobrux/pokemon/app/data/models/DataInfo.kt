package com.dariobrux.pokemon.app.data.models

import com.google.gson.annotations.SerializedName

/**
 *
 * Created by Dario Bruzzese on 20/10/2020.
 *
 */
class DataInfo {
    var count: Int? = 0
    var next: String? = ""
    var previous: String? = ""

    @SerializedName("results")
    var pokemonList: List<Pokemon>? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as DataInfo
        if (count != other.count) return false
        if (next != other.next) return false
        if (previous != other.previous) return false
        if (pokemonList != other.pokemonList) return false
        return true
    }

    override fun hashCode(): Int {
        var result = count ?: 0
        result = 31 * result + (next?.hashCode() ?: 0)
        result = 31 * result + (previous?.hashCode() ?: 0)
        result = 31 * result + (pokemonList?.hashCode() ?: 0)
        return result
    }
}