package com.dariobrux.pokemon.app

import android.graphics.drawable.ColorDrawable
import android.view.View
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

/**
 * Check if the view has background color.
 */
fun hasBackgroundColor(): Matcher<View> {
    return object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description?) {
            description?.appendText("check color")
        }

        override fun matchesSafely(item: View?): Boolean {
            return (item?.background as? ColorDrawable)?.color != null
        }
    }
}