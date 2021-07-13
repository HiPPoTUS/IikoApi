package com.example.iikoapi.menu

import android.util.SparseArray
import androidx.databinding.library.baseAdapters.BR
import com.example.iikoapi.entities.nomenclature.Product
import com.example.iikoapi.utils.adapters.GeneralAdapter
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

