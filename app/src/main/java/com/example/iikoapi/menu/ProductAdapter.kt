package com.example.iikoapi.menu

import android.util.SparseArray
import androidx.databinding.library.baseAdapters.BR
import com.example.iikoapi.entities.datatype.Product
import com.example.iikoapi.utils.GeneralAdapter
import com.example.iikoapi.utils.OnItemClickListener
import com.google.android.material.tabs.TabLayout

class ProductAdapter<T>: GeneralAdapter<T>(){

    private lateinit var hlebListener: TabLayout.OnTabSelectedListener

    fun setHlebListener(hlebListener: TabLayout.OnTabSelectedListener){
        this.hlebListener = hlebListener
    }


    override fun getArguments(position: Int) = super.getArguments(position).apply {
        put(BR.hlebListener, hlebListener)
    }
}