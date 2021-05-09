package com.example.iikoapi.utils

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.example.iikoapi.entities.MerchItem

open class GeneralAdapter<T>:  RecyclerView.Adapter<GeneralAdapter<T>.ViewHolder>() {

    private var items = listOf<T>()
    private var layoutId: Int = 0
    private lateinit var listener: OnItemClickListener<T>

    fun setLayoutId(layoutId: Int){
        this.layoutId = layoutId
    }

    fun setData(items: List<T>){
        this.items = items
    }

    fun getData() = items

    fun setListener(listener: OnItemClickListener<T>){
        this.listener = listener
    }

    fun getItem(position: Int) = items[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        var binding: ViewDataBinding? = null
        try {
            binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false)
        } catch (e: Exception){}

        return ViewHolder(parent.context, binding)

    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getArguments(position))
    }

    override fun getItemViewType(position: Int): Int {
        return layoutId
    }

    inner class ViewHolder(context: Context?, private val binding: ViewDataBinding?)
        : RecyclerView.ViewHolder(binding?.root ?: View(context)){

        fun bind(arguments: SparseArray<Any?>){
            for (i in 0 until arguments.size()) {
                binding?.setVariable(arguments.keyAt(i), arguments.valueAt(i))
            }
            binding?.executePendingBindings()
        }

    }

    open fun getArguments(position: Int) = SparseArray<Any?>().apply {
        put(BR.item, items[position])
        put(BR.position, position)
        if(this@GeneralAdapter::listener.isInitialized){
            put(BR.action, listener) }
    }
}