package com.example.iikoapi.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.iikoapi.R
import com.example.iikoapi.general.bottoNnavigationView
import com.example.iikoapi.openedmenuitem.order
import com.example.iikoapi.startapp.datatype.Product
import com.example.iikoapi.startapp.networking.*
import com.google.android.material.bottomnavigation.BottomNavigationView

fun getCategories():List<String>{
    val tmp = menu.products.filter {
        it.isIncludedInMenu
    }.groupBy {
        it.parentGroup
    }
    return (List(tmp.keys.size){
        menu.groups.find {group ->
            group.id==tmp.keys.elementAt(it)}?.name.toString()
    })
}
fun getProdsByCategory():List<List<Product>>{
    val tmp = menu.products.filter {
        it.isIncludedInMenu
    }.groupBy {
        it.parentGroup
    }
    return tmp.values.toList()
}

fun setBadges(){
    var badge = bottoNnavigationView.getOrCreateBadge(R.id.basket)
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

class Decorations(private val padding: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = 1
        outRect.bottom = padding
        outRect.left = padding / 2
        outRect.right = padding / 2
    }

}