package com.example.iikoapi.general

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.iikoapi.R
import com.example.iikoapi.general.basketadapter.BasketRecycleViewAdapter
import com.example.iikoapi.openedmenuitem.order
import com.example.iikoapi.utils.Decorations
import com.example.iikoapi.utils.getProdsByCategory
import com.example.iikoapi.utils.setBadges
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_general.*


class BasketFragment(var contextMy : Context, var  navView : BottomNavigationView) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_basket, container, false)

        val recyclerViewForBasket = view.findViewById<RecyclerView>(R.id.recycler_view_for_basket)
        val clear_basket = view.findViewById<Button>(R.id.clear_basket)
        val text_empty_basket = view.findViewById<TextView>(R.id.text_empty_basket)

        if(order.items.isEmpty())
            clear_basket.text = "Вернуться в меню"
        else {
            text_empty_basket.alpha = 0f
            clear_basket.text = "Очистить карзину"
        }

        val recyclerViewAdapterForBasket = BasketRecycleViewAdapter(clear_basket, text_empty_basket)
        recyclerViewForBasket.addItemDecoration(Decorations(10))

        recyclerViewForBasket.apply {
            layoutManager = LinearLayoutManager(context)
            recyclerViewForBasket.adapter = recyclerViewAdapterForBasket
        }
        recyclerViewAdapterForBasket.submitList(order.items)

        clear_basket.setOnClickListener {
            if(order.items.isNotEmpty()) {
                order.items.clear()
                recyclerViewAdapterForBasket.notifyDataSetChanged()
                setBadges()
                text_empty_basket.alpha = 1f
                clear_basket.text = "Вернуться в меню"
            }
            else {
                navView.menu.getItem(0).isChecked = true
                fragmentManager!!.beginTransaction()
                    .setCustomAnimations(R.anim.enter_anim_left, R.anim.exit_anim_left)
                    .replace(R.id.fragment_container, MenuFragment(0, contextMy), "1").commit()
            }
        }

        return view
    }

}