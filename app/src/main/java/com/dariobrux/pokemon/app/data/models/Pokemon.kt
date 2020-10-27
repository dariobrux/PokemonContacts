package com.dariobrux.pokemon.app.data.models

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 *
 * Created by Dario Bruzzese on 20/10/2020.
 *
 * This class represents the model of a pokémon.
 * It's used by Room database as table, storing the basic information
 * about the single pokémon.
 *
 */
@Entity(tableName = "pokemon")
class Pokemon : Serializable {

    @PrimaryKey
    @NonNull
    var name: String = ""

    var url: String? = ""

    var urlPicture: String? = ""

    var num: Int? = -1

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Pokemon
        if (name != other.name) return false
        if (url != other.url) return false
        if (urlPicture != other.urlPicture) return false
        if (num != other.num) return false
        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + (url?.hashCode() ?: 0)
        result = 31 * result + (urlPicture?.hashCode() ?: 0)
        result = 31 * result + (num?.hashCode() ?: 0)
        return result
    }
}