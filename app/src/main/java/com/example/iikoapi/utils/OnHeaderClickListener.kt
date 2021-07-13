package com.example.iikoapi.utils

import android.view.View
import android.widget.ImageView

interface OnHeaderClickListener {

    fun onClick(view: View, position: Int, arrow: ImageView)
}