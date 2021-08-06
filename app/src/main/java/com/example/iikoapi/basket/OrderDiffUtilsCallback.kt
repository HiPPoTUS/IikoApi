package com.example.iikoapi.basket

import androidx.recyclerview.widget.DiffUtil
import com.example.iikoapi.entities.basket.BasketItemEntity

class OrderDiffUtilsCallback(
    private val oldList: List<BasketItemEntity>,
    private val newList: List<BasketItemEntity>
) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.basketItem.product.id == newItem.basketItem.product.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newProduct = newList[newItemPosition]
        return oldItem == newProduct
    }

}