package com.example.iikoapi.utils.adapters

import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.iikoapi.entities.RelationShip
import com.example.iikoapi.utils.RemoveDiffUtilsCallback

class ExpandableScrollableAdapter<T>: ExpandableAdapter<T>() {

    lateinit var nestedScrollView: NestedScrollView
    lateinit var removeRecyclerView: RecyclerView
    var space = 0

    override fun expandRow(position: Int){

        super.expandRow(position)

        nestedScrollView.postDelayed({
            nestedScrollView.smoothScrollTo(
                0,
                removeRecyclerView.y.toInt()
            )
        }, 200)

    }

    override fun collapseRow(position: Int){
        val newItems = items.map { it }.toMutableList()
        val nextPosition = position + 1
        outerloop@ while (true) {

            if (nextPosition == newItems.size || newItems[nextPosition].owner == RelationShip.ParentItem) {
                break@outerloop
            }

            newItems.removeAt(nextPosition)
        }

        nestedScrollView.smoothScrollTo(0, (space + removeRecyclerView.y).toInt())

        nestedScrollView.postDelayed({
            val productDiffResult =
                DiffUtil.calculateDiff(RemoveDiffUtilsCallback(this.items, newItems))
            this.items = newItems
            productDiffResult.dispatchUpdatesTo(this)
        }, 200)

    }

}