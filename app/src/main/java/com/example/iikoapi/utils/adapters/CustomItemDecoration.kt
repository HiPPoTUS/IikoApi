package com.example.iikoapi.utils.adapters

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CustomItemDecoration(
    val top: Int = 8,
    val bottom: Int = 8,
    val start: Int = 8,
    val end: Int = 8,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.set(8, 8, 8, 8)
    }

}