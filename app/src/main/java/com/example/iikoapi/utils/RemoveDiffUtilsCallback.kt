package com.example.iikoapi.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.iikoapi.entities.ChildRemove
import com.example.iikoapi.entities.ExpandableEntity


class RemoveDiffUtilsCallback(private val oldList: List<ExpandableEntity<*>>, private val newList: List<ExpandableEntity<*>>) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem: ExpandableEntity<*> = oldList[oldItemPosition]
        val newItem: ExpandableEntity<*> = newList[newItemPosition]
        return oldItem.owner == newItem.owner
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem: ExpandableEntity<*> = oldList[oldItemPosition]
        val newProduct: ExpandableEntity<*> = newList[newItemPosition]
        return oldItem.toString() == newProduct.toString()
    }

//    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
//        val oldItem = oldList[oldItemPosition]
//        val newItem = newList[newItemPosition]
//
//        return Change(
//            oldItem,
//            newItem)
//    }

}
