package com.example.iikoapi.menu

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import com.example.iikoapi.databinding.MenuRecyclerViewBinding
import com.example.iikoapi.entities.GroupProducts
import com.example.iikoapi.entities.MerchItem
import com.example.iikoapi.entities.datatype.Product
import com.example.iikoapi.utils.GeneralAdapter
import com.example.iikoapi.utils.OnItemClickListener

class MenuAdapter<T>: GeneralAdapter<T>(){

    private lateinit var innerListener: OnItemClickListener<Product>

    fun setInnerListener(innerListener: OnItemClickListener<Product>){
        this.innerListener = innerListener
    }


    override fun getArguments(position: Int) = SparseArray<Any?>().apply {
        put(BR.groupProduct, getItem(position))
        put(BR.buyListener, innerListener)
    }
}

