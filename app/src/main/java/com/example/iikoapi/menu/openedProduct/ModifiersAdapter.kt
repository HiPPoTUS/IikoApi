package com.example.iikoapi.menu.openedProduct

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.iikoapi.entities.MerchItem
import com.example.iikoapi.utils.adapters.GeneralAdapter

@Suppress("UNCHECKED_CAST")
class ModifiersAdapter<T> : GeneralAdapter<T>() {

    fun getModifiers() = (getData() as List<MerchItem>)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        var binding: ViewDataBinding? = null
        try {
            binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false)

        } catch (e: Exception) {
        }

        return ViewHolder(parent.context, binding)
    }

}