package com.example.iikoapi.general

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.iikoapi.R
import com.example.iikoapi.general.basketadapter.BasketRecycleViewAdapter
import com.example.iikoapi.openedmenuitem.order
import com.example.iikoapi.utils.getProdsByCategory
import com.example.iikoapi.utils.setBadges
import com.google.android.material.bottomnavigation.BottomNavigationView


class BasketFragment() : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_basket, container, false)

        val recyclerViewForBasket = view.findViewById<RecyclerView>(R.id.recycler_view_for_basket)
        val clear_basket = view.findViewById<Button>(R.id.clear_basket)

        val recyclerViewAdapterForBasket = BasketRecycleViewAdapter()

        recyclerViewForBasket.apply {
            layoutManager = LinearLayoutManager(context)
            recyclerViewForBasket.adapter = recyclerViewAdapterForBasket
        }
        recyclerViewAdapterForBasket.submitList(order.items)

        clear_basket.setOnClickListener {
            order.items.clear()
            recyclerViewAdapterForBasket.notifyDataSetChanged()
            setBadges()
        }

        return view
    }

}