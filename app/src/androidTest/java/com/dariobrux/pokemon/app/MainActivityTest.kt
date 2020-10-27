package com.dariobrux.pokemon.app

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.dariobrux.pokemon.app.ui.MainActivity
import org.junit.After
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    lateinit var scenario: ActivityScenario<MainActivity>

    @After
    fun cleanup() {
        scenario.close()
    }

    @Test
    fun useAppContext() {
        scenario = launchActivity()
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("com.dariobrux.pokemon.app", appContext.packageName)
    }

    @Test
    fun testIsRecyclerDisplayed() {
        scenario = launchActivity()
        onView(withId(R.id.recycler)).check(matches(isDisplayed()))
    }

    @Test
    fun testRecyclerSwipeDownAndUp() {
        scenario = launchActivity()
        onView(withId(R.id.recycler)).perform(swipeUp())
        onView(withId(R.id.recycler)).perform(swipeDown())
    }

    @Test
    fun testIsButtonSortDisplayed() {
        scenario = launchActivity()
        onView(withId(R.id.recycler)).perform(swipeUp())
    }

    @Test
    fun testButtonSortClicked() {
        scenario = launchActivity()
        onView(withId(R.id.recycler)).perform(swipeUp())
        onView(withId(R.id.recycler)).check(matches(isDisplayed()))
    }

    @Test
    fun testFirstPokemonClicked() {
        scenario = launchActivity()
        Thread.sleep(2000)
        onView(withText("Bulbasaur")).perform(click())
    }

}