package com.dariobrux.pokemon.app.other.extensions

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable


/**
 * Convert a [Drawable] object to a [ColorDrawable] object
 * if is possible.
 */
fun Drawable.toColorDrawable() : ColorDrawable? {
    return this as? ColorDrawable
}