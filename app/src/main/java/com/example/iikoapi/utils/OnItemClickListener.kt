package com.example.iikoapi.utils

import android.view.View
import com.google.android.material.tabs.TabLayout

interface OnItemClickListener<T> {

    fun onClick(view: View, item: T, position: Int)

}