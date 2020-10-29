package com.dariobrux.pokemon.app

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dariobrux.pokemon.app.ui.MainActivity
import com.dariobrux.pokemon.app.ui.main.MainAdapter
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
        onView(withId(R.id.recycler)).perform(RecyclerViewActions.actionOnItemAtPosition<MainAdapter.ViewHolder>(0, click()));
        onView(withId(R.id.containerRoot)).check(matches(isDisplayed()))
    }

    @Test
    fun testNameVisible() {
        scenario = launchActivity()
        Thread.sleep(2000)
        onView(withId(R.id.recycler)).perform(RecyclerViewActions.actionOnItemAtPosition<MainAdapter.ViewHolder>(0, click()));
        Thread.sleep(2000)
        onView(withId(R.id.txtName)).check(matches(isDisplayed()))
    }

    @Test
    fun testHasExperienceVisible() {
        scenario = launchActivity()
        Thread.sleep(2000)
        onView(withId(R.id.recycler)).perform(RecyclerViewActions.actionOnItemAtPosition<MainAdapter.ViewHolder>(0, click()));
        Thread.sleep(2000)
        onView(withId(R.id.txtExperience)).check(matches(isDisplayed()))
    }

    @Test
    fun testHasBackgroundColor() {
        scenario = launchActivity()
        Thread.sleep(2000)
        onView(withId(R.id.recycler)).perform(RecyclerViewActions.actionOnItemAtPosition<MainAdapter.ViewHolder>(0, click()));
        Thread.sleep(2000)
        onView(withId(R.id.containerRoot)).check(matches(hasBackgroundColor()))
    }
}
