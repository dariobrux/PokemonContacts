package com.dariobrux.pokemon.app.ui.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.dariobrux.pokemon.app.other.extensions.dpToPx

/**
 *
 * Created by Dario Bruzzese on 22/10/2020.
 *
 * This class is the ItemDecoration useful for the RecyclerView in list visualization.
 */
class LinearSpaceItemDecoration(private val space: Int) : ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        parent.adapter?.let {
            val position = parent.getChildAdapterPosition(view)
            if (position < it.itemCount) {
                outRect.bottom = space.dpToPx()
            }
        }
    }
}