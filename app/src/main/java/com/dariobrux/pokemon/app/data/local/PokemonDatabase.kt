package com.dariobrux.pokemon.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dariobrux.pokemon.app.data.models.Pokemon
import com.dariobrux.pokemon.app.data.models.PokemonData

/**
 *
 * Created by Dario Bruzzese on 21/10/2020.
 *
 * This class is the representation of the database.
 *
 */

@Database(
    exportSchema = false, version = 1, entities = [
        Pokemon::class,
        PokemonData::class
    ]
)
abstract class PokemonDatabase : RoomDatabase() {

    companion object {
        private const val DB_NAME = "pokemon.db"

        private var instance: PokemonDatabase? = null

        @Synchronized
        fun getInstance(context: Context): PokemonDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext, PokemonDatabase::class.java, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }
    }

    abstract fun pokemonDao(): PokemonDao
}