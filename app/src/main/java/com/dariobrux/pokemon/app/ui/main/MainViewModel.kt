package com.dariobrux.pokemon.app.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dariobrux.pokemon.app.data.models.DataInfo
import com.dariobrux.pokemon.app.data.models.Pokemon
import com.dariobrux.pokemon.app.other.Resource
import com.github.tamir7.contacts.Contact


/**
 *
 * Created by Dario Bruzzese on 22/10/2020.
 *
 */
class MainViewModel @ViewModelInject constructor(private val mainRepository: MainRepository) : ViewModel() {

    var combinedItemsList = mutableListOf<Any>()

    /**
     * The Adapter to show the items in list.
     */
    var adapter: MainAdapter? = null

    /**
     * @return the pokemon list.
     */
    fun getPokemonList(): LiveData<Resource<DataInfo>>? {
        return mainRepository.getPokemon()
    }

    /**
     * Get the list of the contacts.
     */
    fun getContactList(): List<Contact> {
        return mainRepository.getContactList()
    }

    /**
     * Refresh the pokemon list, reloading from the first pokemon.
     */
    fun refreshPokemon(): LiveData<Resource<DataInfo>>? {
        resetOffset()
        return getPokemonList()
    }

    fun resetOffset() {
        mainRepository.resetOffset()
    }

    /**
     * This function combine every item of the pokemon list with every item of the contacts list.
     * @param contactList the list of contacts.
     * @param pokemonList the list of pokemon.
     */
    fun getCombinedContactsAndPokemon(contactList: List<Contact>, pokemonList: List<Pokemon>): List<Any> {
        return pokemonList.zip(contactList) { a, b -> listOf(a, b) }.flatten().toMutableList()
    }
}