package com.dariobrux.pokemon.app.other.extensions

import android.animation.ValueAnimator
import android.view.View
import androidx.cardview.widget.CardView

/**
 * Animate the background color starting from a color, ending to another color using
 * the argb evaluator.
 * @param startColor the start color.
 * @param toColor the final color.
 */
fun View.animateBackgroundColor(startColor: Int, toColor: Int) {
    val anim: ValueAnimator = ValueAnimator.ofArgb(startColor, toColor)
    anim.duration = 200
    anim.addUpdateListener {
        val color = it.animatedValue as Int
        this.setBackgroundColor(color)
    }
    anim.start()
}

/**
 * Animate the card background color of a [CardView] starting from a color, ending to another color using
 * the argb evaluator.
 * @param startColor the start color.
 * @param toColor the final color.
 */
fun CardView.animateCardBackgroundColor(startColor: Int, toColor: Int) {
    val anim: ValueAnimator = ValueAnimator.ofArgb(startColor, toColor)
    anim.duration = 200
    anim.addUpdateListener {
        val color = it.animatedValue as Int
        this.setCardBackgroundColor(color)
    }
    anim.start()
}