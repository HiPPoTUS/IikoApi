package com.example.iikoapi.general

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.iikoapi.R
import com.example.iikoapi.general.basketadapter.BasketRecycleViewAdapter
import com.example.iikoapi.openedmenuitem.order
import com.example.iikoapi.startapp.networking.Iiko
import com.example.iikoapi.startapp.networking.NetworkService
import com.example.iikoapi.utils.Decorations
import com.example.iikoapi.utils.hideKeyboard
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior


class BasketFragment(var contextMy: Context, var navView: BottomNavigationView, var payment: ConstraintLayout) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val iiko = Iiko(contextMy, provider = NetworkService.instance!!, pb = null)
        val view = inflater.inflate(R.layout.fragment_basket, container, false)
//        val api = CPCardApi(contextMy)

        val recyclerViewForBasket = view.findViewById<RecyclerView>(R.id.recycler_view_for_basket)
        val pay_go_to_menu = view.findViewById<Button>(R.id.pay_go_to_menu)
        val text_empty_basket = view.findViewById<TextView>(R.id.text_empty_basket)
        val bottomSheetBehavior = BottomSheetBehavior.from(payment)
        recyclerViewForBasket.visibility = View.GONE

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if(bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED)
                    hideKeyboard(contextMy as AppCompatActivity)
            }
        })

        if(order.items.isEmpty()) {
            recyclerViewForBasket.visibility = View.GONE
            pay_go_to_menu.text = "Вернуться в меню"
        }
        else {
            text_empty_basket.alpha = 0f
            pay_go_to_menu.text = "Оплатить"
            recyclerViewForBasket.visibility = View.VISIBLE
        }

        val recyclerViewAdapterForBasket = BasketRecycleViewAdapter(pay_go_to_menu, text_empty_basket, recyclerViewForBasket, contextMy)
        if (recyclerViewForBasket.itemDecorationCount == 0)
            recyclerViewForBasket.addItemDecoration(Decorations())

        recyclerViewForBasket.apply {
            layoutManager = LinearLayoutManager(context)
            recyclerViewForBasket.adapter = recyclerViewAdapterForBasket
        }
        recyclerViewAdapterForBasket.submitList(order.items)

        pay_go_to_menu.setOnClickListener {
            if(order.items.isNotEmpty()) {
                dat(iiko, contextMy, bottomSheetBehavior).execute()
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

class dat(var iiko : Iiko, var context: Context, var btsh: BottomSheetBehavior<ConstraintLayout>) : AsyncTask<Void, Void, Void>() {
    override fun doInBackground(vararg params: Void?): Void? {
        iiko.authentication()
        iiko.getOrganisation(0)
        return null
    }
    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
        if (btsh.state != BottomSheetBehavior.STATE_EXPANDED)
            btsh.state = BottomSheetBehavior.STATE_EXPANDED
    }
}