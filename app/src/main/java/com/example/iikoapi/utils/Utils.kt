package com.example.iikoapi.utils

import android.app.Activity
import android.graphics.Color
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.iikoapi.R
import com.example.iikoapi.general.bottomNavigationView
import com.example.iikoapi.openedmenuitem.order

fun setBadges(){
    val badge = bottomNavigationView.getOrCreateBadge(R.id.basket)
    order.update()
    badge.backgroundColor=Color.rgb(255,102,0)
    if(order.totalItems == 0){
        badge.isVisible = false
        badge.clearNumber()
    }
    else{
        badge.isVisible = true
        badge.number = order.totalItems
    }

}

fun hideKeyboard(activity: AppCompatActivity) {
    val imm: InputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    var view = activity.currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(activity)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
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