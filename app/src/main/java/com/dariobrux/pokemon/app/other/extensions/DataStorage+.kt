package com.dariobrux.pokemon.app.other.extensions

import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.emptyPreferences
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import java.io.IOException

/**
 * Coroutine method that checks if there is a [PreferencesKey] in the [DataStore].
 * It catch any exception occurred by reading the data. If data is available, it will be
 * mapped into the preference, then launch the parameter function on success.
 * @param func function to invoke on success.
 */
suspend inline fun <reified T> DataStore<Preferences>.getFromLocalStorage(PreferencesKey: Preferences.Key<T>, crossinline func: T.() -> Unit, crossinline defaultFunc: () -> Unit) {
    data.catch {
        if (it is IOException) {
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map {
        it[PreferencesKey]
    }.collect {
        if (it != null ) {
            func.invoke(it as T)
        } else {
            defaultFunc.invoke()
        }
    }
}

/**
 * Coroutine method that insert a value of a particular [Preferences.Key] in the [DataStore].
 * @param key the key of a preference.
 * @param value the value to insert.
 */
suspend inline fun <reified T> DataStore<Preferences>.storeValue(key: Preferences.Key<T>, value: Any) {
    this.edit {
        it[key] = value as T
    }
}

/**
 * Coroutine method that read a value of a particular [Preferences.Key] from the [DataStore].
 * @param key the key of a preference.
 * @param responseFunc function to invoke on success.
 */
suspend inline fun <reified T> DataStore<Preferences>.readValue(key: Preferences.Key<T>, crossinline responseFunc: T.() -> Unit, crossinline defaultFunc: () -> Unit) {
    this.getFromLocalStorage(key, {
        responseFunc.invoke(this)
    }, {
        defaultFunc.invoke()
    })
}