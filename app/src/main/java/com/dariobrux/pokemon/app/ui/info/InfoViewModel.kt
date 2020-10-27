package com.dariobrux.pokemon.app.ui.info

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.dariobrux.pokemon.app.data.models.PokemonData

/**
 *
 * Created by Dario Bruzzese on 22/10/2020.
 *
 */
class InfoViewModel @ViewModelInject constructor(private val infoRepository: InfoRepository) : ViewModel() {

    /**
     * @param name the name of the pokemon
     * @param url the url to call to get the info.
     * @return a [PokemonData] observable object with the info.
     */
    fun getPokemonData(name: String, url: String) = infoRepository.getPokemonData(name, url)

}