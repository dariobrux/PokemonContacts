package com.dariobrux.pokemon.app.other.extensions

import android.app.Activity
import android.content.res.Resources
import com.dariobrux.pokemon.app.ui.MainActivity

/**
 * Cast the activity to the [MainActivity].
 * @return the [MainActivity] class or null.
 */
fun Activity.toMainActivity() : MainActivity? {
    return this as? MainActivity
}