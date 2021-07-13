package com.example.iikoapi.utils.adapters

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.iikoapi.entities.ExpandableEntity
import com.example.iikoapi.entities.ExpandableState
import com.example.iikoapi.entities.RelationShip
import com.example.iikoapi.utils.OnHeaderClickListener
import com.example.iikoapi.utils.OnItemClickListener
import com.example.iikoapi.utils.RemoveDiffUtilsCallback


@Suppress("UNCHECKED_CAST")
open class ExpandableAdapter<T> :
    RecyclerView.Adapter<ExpandableAdapter<T>.ViewHolder>() {

    var items = mutableListOf<ExpandableEntity<T>>()
    private var parentLayoutId: Int = 0
    private var childLayoutId: Int = 0

    lateinit var context: Context

    lateinit var listener: OnItemClickListener<T>

    fun setParentLayoutId(parentLayoutId: Int) {
        this.parentLayoutId = parentLayoutId
    }

    fun setChildLayoutId(childLayoutId: Int) {
        this.childLayoutId = childLayoutId
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

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getArguments(position))
    }

    private val headerListener = object : OnHeaderClickListener {
        override fun onClick(view: View, position: Int, arrow: ImageView) {
            arrow.animate().setDuration(200).rotation(if(arrow.rotation == 90f) 0f else 90f).start()
            if (items[position].state == ExpandableState.Collapsed) {
                items[position].state = ExpandableState.Expanded
                expandRow(position)
            } else {
                items[position].state = ExpandableState.Collapsed
                collapseRow(position)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position].owner) {
            is RelationShip.ChildItem -> childLayoutId
            is RelationShip.ParentItem -> parentLayoutId
        }
    }


    inner class ViewHolder(context: Context?, val binding: ViewDataBinding?) :
        RecyclerView.ViewHolder(binding?.root ?: View(context)) {

        fun bind(arguments: SparseArray<Any?>) {
            for (i in 0 until arguments.size()) {
                binding?.setVariable(arguments.keyAt(i), arguments.valueAt(i))
            }
            binding?.executePendingBindings()
        }

    }

    open fun getArguments(position: Int) = SparseArray<Any?>().apply {
        put(BR.item, items[position])
        put(BR.position, position)
        if(items[position].owner == RelationShip.ParentItem){
            put(BR.action, headerListener)
        }
        else if(items[position].owner == RelationShip.ChildItem && this@ExpandableAdapter::listener.isInitialized){
            put(BR.action, listener)
        }

    }

    open fun expandRow(position: Int){

        val newItems = items.map { it }.toMutableList()
        val row = items[position]
        var nextPosition = position
        for (child in row.children!!) {
            newItems.add(++nextPosition, child as ExpandableEntity<T>)
        }

        val productDiffResult =
            DiffUtil.calculateDiff(RemoveDiffUtilsCallback(this.items, newItems))
        this.items = newItems
        productDiffResult.dispatchUpdatesTo(this)

    }

    open fun collapseRow(position: Int){
        val newItems = items.map { it }.toMutableList()
        val nextPosition = position + 1
        outerloop@ while (true) {

            if (nextPosition == newItems.size || newItems[nextPosition].owner == RelationShip.ParentItem) {
                break@outerloop
            }

            newItems.removeAt(nextPosition)
        }

        val productDiffResult =
            DiffUtil.calculateDiff(RemoveDiffUtilsCallback(this.items, newItems))
        this.items = newItems
        productDiffResult.dispatchUpdatesTo(this)

    }


}