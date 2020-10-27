package com.dariobrux.pokemon.app.other.extensions

import android.graphics.Color

/**
 * Given a color int, change its alpha between
 * 0 and 255.
 * @param alpha the new value
 * @return a new color int
 */
fun Int.changeAlpha(alpha: Int) : Int{
    val red = Color.red(this)
    val green = Color.green(this)
    val blue = Color.blue(this)
    return Color.argb(alpha, red, green, blue)
}