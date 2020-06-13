package com.example.iikoapi.general.menuadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.iikoapi.R
import com.example.iikoapi.startapp.datatype.Product

class MenuAdapter(private var items : List<List<Product>>) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    inner class MenuViewHolder(view : View) : RecyclerView.ViewHolder(view){
        private val rView = view.findViewById<RecyclerView>(R.id.recycler_view)

        private lateinit var recycleViewAdapter: MenuRecycleViewAdapter

        fun bind(data : List<Product>, position: Int) {
            rView.apply {
                layoutManager = LinearLayoutManager(context)
                recycleViewAdapter = MenuRecycleViewAdapter(context, position)
                adapter = recycleViewAdapter
            }

            recycleViewAdapter.submitList(data)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.menu_recycler_view,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(items[position], position)
    }
}