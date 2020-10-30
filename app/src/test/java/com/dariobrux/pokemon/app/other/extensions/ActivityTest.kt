package com.dariobrux.pokemon.app.other.extensions

import com.dariobrux.pokemon.app.ui.MainActivity
import com.dariobrux.pokemon.app.ui.main.MainFragment
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@Config(manifest= Config.NONE)
@RunWith(RobolectricTestRunner::class)
class ActivityTest : TestCase() {

    @Test
    fun testToMainActivity() {
        val mainFragment = Mockito.mock(MainFragment::class.java)
        Mockito.`when`(mainFragment.requireActivity()).thenReturn(MainActivity())
        assertTrue(mainFragment.requireActivity().toMainActivity() is MainActivity)
    }
}