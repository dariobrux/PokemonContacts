package com.dariobrux.pokemon.app.data.models

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 *
 * Created by Dario Bruzzese on 21/10/2020.
 *
 */
@Entity(tableName = "pokemonData")
class PokemonData {
    @PrimaryKey
    @NonNull
    var id: Int = 0

    var name: String? = ""

    @SerializedName("base_experience")
    var baseExperience = 0

    var height = 0

    var weight = 0
}