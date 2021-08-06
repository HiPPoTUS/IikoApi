package com.example.iikoapi.basket

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import com.example.iikoapi.entities.basket.BasketItemEntity
import com.example.iikoapi.entities.basket.BasketEntity
import com.example.iikoapi.utils.adapters.GeneralAdapter

class BasketAdapter : GeneralAdapter<BasketItemEntity>() {

    var footerLayout = 0

    private var price = 0

    var order: BasketEntity = BasketEntity(mutableListOf())
        set(value) {
            field = value
            setData(value.items)
//            price = value.getAllPrice()
        }

    fun updateOrder(oldItems: List<BasketItemEntity>){
        val orderDiffUtilsCallback =
            DiffUtil.calculateDiff(OrderDiffUtilsCallback(oldItems, getData()))
        orderDiffUtilsCallback.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        var binding: ViewDataBinding? = null
        try {
            binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false)
        } catch (e: Exception) {
        }

        return ViewHolder(parent.context, binding)

    }

    override fun getItemCount() = super.getItemCount() + 1

    override fun getItemViewType(position: Int) =
        if (position == getData().size) {
            footerLayout
        } else {
            super.getItemViewType(position)
        }

    override fun getArguments(position: Int): SparseArray<Any?> {
        return if (position == getData().size) {
            SparseArray<Any?>().apply {
                put(BR.price, order.allPrice)
            }
        } else {
            super.getArguments(position)
        }
    }
}