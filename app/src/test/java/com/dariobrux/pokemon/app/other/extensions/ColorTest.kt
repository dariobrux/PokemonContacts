package com.dariobrux.pokemon.app.other.extensions

import android.graphics.Color
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(manifest= Config.NONE)
@RunWith(RobolectricTestRunner::class)
class ColorTest : TestCase() {

    @Test
    fun testChangeAlpha1() {
        val color = Color.rgb(80, 80, 80)
        val newColor = color.changeAlpha(80)
        assertEquals(Color.argb(80, 80, 80, 80), newColor)
    }

    @Test
    fun testChangeAlpha2() {
        val color = Color.argb(80, 80, 80, 80)
        val newColor = color.changeAlpha(0)
        assertEquals(Color.argb(0, 80, 80, 80), newColor)
    }
}