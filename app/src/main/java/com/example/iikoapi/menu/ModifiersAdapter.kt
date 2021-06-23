package com.example.iikoapi.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.iikoapi.entities.MerchItem
import com.example.iikoapi.utils.GeneralAdapter

@Suppress("UNCHECKED_CAST")
class ModifiersAdapter<T>: GeneralAdapter<T>() {

    private var width = 0

    fun setWidth(width: Int){
        this.width = width
    }

    fun changeOrder(position: Int){
        (getData() as List<MerchItem>)[position].isSelected = !(getData() as List<MerchItem>)[position].isSelected
    }

    fun getModifiers() = (getData() as List<MerchItem>)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        var binding: ViewDataBinding? = null
        try {
            binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false)

        } catch (e: Exception){}

        return ViewHolder(parent.context, binding.also {
            it?.root?.layoutParams = ViewGroup.LayoutParams(width, width)
        })
    }

}