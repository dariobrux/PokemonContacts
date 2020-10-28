package com.dariobrux.pokemon.app.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dariobrux.pokemon.app.data.models.ContactData
import com.dariobrux.pokemon.app.data.models.DataInfo
import com.dariobrux.pokemon.app.data.models.Pokemon
import com.dariobrux.pokemon.app.other.Resource
import java.util.*


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
    fun getContactList(): List<ContactData> {
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
    fun getCombinedContactsAndPokemon(contactList: List<ContactData>, pokemonList: List<Pokemon>): List<Any> {
//        return pokemonList.zip(contactList).flatMap {
//            listOf(it.first, it.second)
//        } + (if (pokemonList.size > contactList.size) {
//            pokemonList.drop(contactList.size)
//        } else {
//            contactList.drop(pokemonList.size)
//        })
        return (pokemonList + contactList).toList<Any>().sortedBy {
            when (it) {
                is Pokemon -> it.name.toLowerCase(Locale.getDefault())
                is ContactData -> it.displayName?.toLowerCase(Locale.getDefault()) ?: ""
                else -> ""
            }
        }

    }
}