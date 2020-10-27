package com.dariobrux.pokemon.app

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dariobrux.pokemon.app.ui.MainActivity
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class InfoFragmentTest {

    lateinit var scenario: ActivityScenario<MainActivity>

    @After
    fun cleanup() {
        scenario.close()
    }

    @Test
    fun testInfoScreenVisible() {
        scenario = launchActivity()
        Thread.sleep(2000)
        onView(withText("Bulbasaur")).perform(click())
        onView(withId(R.id.containerRoot)).check(matches(isDisplayed()))
    }

    @Test
    fun testCardVisible() {
        scenario = launchActivity()
        Thread.sleep(2000)
        onView(withText("Bulbasaur")).perform(click())
        Thread.sleep(2000)
        onView(withId(R.id.card)).check(matches(isDisplayed()))
    }

    @Test
    fun testNameVisible() {
        scenario = launchActivity()
        Thread.sleep(2000)
        onView(withText("Bulbasaur")).perform(click())
        Thread.sleep(2000)
        onView(withId(R.id.txtName)).check(matches(isDisplayed()))
        onView(withText("Bulbasaur")).check(matches(isDisplayed()))
    }

    @Test
    fun testExperience() {
        scenario = launchActivity()
        Thread.sleep(2000)
        onView(withText("Bulbasaur")).perform(click())
        Thread.sleep(2000)
        onView(withText("Base experience: 64")).check(matches(isDisplayed()))
    }

    @Test
    fun testHeight() {
        scenario = launchActivity()
        Thread.sleep(2000)
        onView(withText("Bulbasaur")).perform(click())
        Thread.sleep(2000)
        onView(withText("Height: 7")).check(matches(isDisplayed()))
    }

    @Test
    fun testWeight() {
        scenario = launchActivity()
        Thread.sleep(2000)
        onView(withText("Bulbasaur")).perform(click())
        Thread.sleep(2000)
        onView(withText("Weight: 69")).check(matches(isDisplayed()))
    }
}