package com.example.iikoapi.utils

import android.graphics.Color
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.iikoapi.R
import com.example.iikoapi.general.bottoNnavigationView
import com.example.iikoapi.openedmenuitem.order
import com.example.iikoapi.startapp.networking.*
import com.google.android.material.bottomnavigation.BottomNavigationView

fun setBadges(){
    val badge = bottoNnavigationView.getOrCreateBadge(R.id.basket)
    badge.backgroundColor=Color.rgb(255,102,0)
    order.update()
    if(order.totalItems == 0){
        badge.isVisible = false
        badge.clearNumber()
    }
    else{
        badge.isVisible = true
        badge.number = order.totalItems
    }

}

class Decorations : RecyclerView.ItemDecoration() {

    private val padding = 10

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = 1
        outRect.bottom = padding
        outRect.left = padding / 2
        outRect.right = padding / 2
    }

}