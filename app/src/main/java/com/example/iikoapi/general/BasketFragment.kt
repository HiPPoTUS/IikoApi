package com.example.iikoapi.general

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.iikoapi.R
import com.example.iikoapi.general.basketadapter.BasketRecycleViewAdapter
import com.example.iikoapi.openedmenuitem.order
import com.example.iikoapi.startapp.datatype.PayApiResponse
import com.example.iikoapi.startapp.datatype.PayRequestArgs
import com.example.iikoapi.startapp.datatype.Post3dsRequestArgs
import com.example.iikoapi.startapp.datatype.Transaction
import com.example.iikoapi.startapp.networking.CP
import com.example.iikoapi.startapp.networking.Iiko
import com.example.iikoapi.startapp.networking.CpNetworkService
import com.example.iikoapi.startapp.networking.IikoNetworkService
import com.example.iikoapi.utils.Decorations
import com.example.iikoapi.utils.hideKeyboard
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior

class BasketFragment(var contextMy: Context, var navView: BottomNavigationView, var payment: ConstraintLayout) : Fragment(){
    val iiko = Iiko(contextMy,provider = IikoNetworkService.instance!!,pb = null)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_basket, container, false)
        val recyclerViewForBasket = view.findViewById<RecyclerView>(R.id.recycler_view_for_basket)
        val payGoToMenu = view.findViewById<Button>(R.id.pay_go_to_menu)
        val textEmptyBasket = view.findViewById<TextView>(R.id.text_empty_basket)
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
            payGoToMenu.text = "Вернуться в меню"
        }
        else {
            textEmptyBasket.alpha = 0f
            payGoToMenu.text = "Оплатить"
            recyclerViewForBasket.visibility = View.VISIBLE
        }

        val recyclerViewAdapterForBasket = BasketRecycleViewAdapter(payGoToMenu, textEmptyBasket, recyclerViewForBasket, contextMy)
        if (recyclerViewForBasket.itemDecorationCount == 0)
            recyclerViewForBasket.addItemDecoration(Decorations())

        recyclerViewForBasket.apply {
            layoutManager = LinearLayoutManager(context)
            recyclerViewForBasket.adapter = recyclerViewAdapterForBasket
        }
        recyclerViewAdapterForBasket.submitList(order.items)

        payGoToMenu.setOnClickListener {
            if(order.items.isNotEmpty()) {
                Dat(iiko, contextMy, bottomSheetBehavior).execute()
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



