package com.example.iikoapi.general

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.iikoapi.R
import com.example.iikoapi.general.basketadapter.BasketRecycleViewAdapter
import com.example.iikoapi.openedmenuitem.order
import com.example.iikoapi.utils.Decorations
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior


class BasketFragment(var contextMy : Context, var  navView : BottomNavigationView, var payment : RelativeLayout) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_basket, container, false)

        val recyclerViewForBasket = view.findViewById<RecyclerView>(R.id.recycler_view_for_basket)
        val pay_go_to_menu = view.findViewById<Button>(R.id.pay_go_to_menu)
        val text_empty_basket = view.findViewById<TextView>(R.id.text_empty_basket)
        val bottomSheetBehavior = BottomSheetBehavior.from(payment)

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if(bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED)
                    hideKeyboard(contextMy as AppCompatActivity)
            }
        })

        if(order.items.isEmpty())
            pay_go_to_menu.text = "Вернуться в меню"
        else {
            text_empty_basket.alpha = 0f
            pay_go_to_menu.text = "Оплатить"
        }

        val recyclerViewAdapterForBasket = BasketRecycleViewAdapter(pay_go_to_menu, text_empty_basket)
        if (recyclerViewForBasket.itemDecorationCount == 0)
            recyclerViewForBasket.addItemDecoration(Decorations())

        recyclerViewForBasket.apply {
            layoutManager = LinearLayoutManager(context)
            recyclerViewForBasket.adapter = recyclerViewAdapterForBasket
        }
        recyclerViewAdapterForBasket.submitList(order.items)

        pay_go_to_menu.setOnClickListener {
            if(order.items.isNotEmpty()) {
                if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED)
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED;
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

    fun hideKeyboard(activity: AppCompatActivity) {
        val imm: InputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}