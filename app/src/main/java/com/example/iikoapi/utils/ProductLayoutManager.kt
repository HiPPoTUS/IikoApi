package com.example.iikoapi.utils

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager

class ProductLayoutManager(context: Context, numberOfColumns: Int): GridLayoutManager(context, numberOfColumns) {

    var isScrollable = false
    override fun canScrollVertically(): Boolean {
        return isScrollable && super.canScrollVertically()
    }
}