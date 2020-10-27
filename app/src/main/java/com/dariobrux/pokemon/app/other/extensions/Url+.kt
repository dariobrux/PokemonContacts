package com.dariobrux.pokemon.app.other.extensions

/**
 *
 * Created by Dario Bruzzese on 20/10/2020.
 *
 * Use this class to put all the extension methods
 * belonging to a
 */

/**
 * Split an url by "/" to get the last index. It contains the id.
 * @return the id
 */
fun String.getIdFromUrl() : Int {
    val splitUrl = this.split("/").toMutableList()
    splitUrl.removeLast()
    return splitUrl.last().toIntOrNull() ?: -1
}