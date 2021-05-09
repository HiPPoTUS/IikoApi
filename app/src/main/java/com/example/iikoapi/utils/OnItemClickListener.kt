package com.example.iikoapi.utils

import android.view.View

interface OnItemClickListener<T> {

    fun onClick(view: View, item: T, position: Int? = null)
}