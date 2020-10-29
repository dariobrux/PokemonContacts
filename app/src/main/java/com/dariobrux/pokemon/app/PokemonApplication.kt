package com.dariobrux.pokemon.app

import android.app.Application
import com.github.tamir7.contacts.Contacts
import dagger.hilt.android.HiltAndroidApp

/**
 *
 * Created by Dario Bruzzese on 22/10/2020.
 *
 * This is the application class declared in Manifest.
 */

@HiltAndroidApp
class PokemonApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Contacts.initialize(this)
    }
}
