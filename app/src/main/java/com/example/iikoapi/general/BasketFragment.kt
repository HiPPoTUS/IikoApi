package com.example.iikoapi.general

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
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
import kotlinx.android.synthetic.main.fragment_basket.*
import kotlinx.android.synthetic.main.opened_item_for_view_pager.view.*

class BasketFragment(var contextMy: Context, var navView: BottomNavigationView, var payment: ConstraintLayout) : Fragment(){

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_basket, container, false)
        val recyclerViewForBasket = view.findViewById<RecyclerView>(R.id.recycler_view_for_basket)
        val payGoToMenu = view.findViewById<Button>(R.id.pay_go_to_menu)
        val textEmptyBasket = view.findViewById<TextView>(R.id.text_empty_basket)
        val bottomSheetBehavior = BottomSheetBehavior.from(payment)
        val RLBasket = view.findViewById<RelativeLayout>(R.id.RLBasket)
        val infoNameBasket = view.findViewById<TextView>(R.id.infoNameBasket)
        val infoEnergyBasket = view.findViewById<TextView>(R.id.infoEnergyBasket)
        val infoProteinsBasket = view.findViewById<TextView>(R.id.infoProteinsBasket)
        val infoFatsBasket = view.findViewById<TextView>(R.id.infoFatsBasket)
        val infoCarbohydratesBasket = view.findViewById<TextView>(R.id.infoCarbohydratesBasket)
        val infoLayoutBasket = view.findViewById<RelativeLayout>(R.id.infoLayoutBasket)


        RLBasket.setOnTouchListener { _, _ ->
            infoLayoutBasket.animate().alpha(0f).duration = 100
            RLBasket.visibility = View.GONE
            true
        }

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
        recyclerViewAdapterForBasket.setBasketFragment(this)
        if (recyclerViewForBasket.itemDecorationCount == 0)
            recyclerViewForBasket.addItemDecoration(Decorations())

        recyclerViewForBasket.apply {
            layoutManager = LinearLayoutManager(context)
            recyclerViewForBasket.adapter = recyclerViewAdapterForBasket
        }
        recyclerViewAdapterForBasket.submitList(order.items)

        payGoToMenu.setOnClickListener {
            if(order.items.isNotEmpty()) {
                order.items.forEach { it.update() }
                order.update()
                paymentFragment.setCost()
                if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED)
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
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

    fun openInfo(name : String, carbohydrateAmount : String?, energyAmount : String?, fiberAmount : String?, fatAmount : String?){
        infoNameBasket.text = name
        infoCarbohydratesBasket.text = try {
            carbohydrateAmount!!.toInt()
        }catch (e : Exception){}.toString()
        infoEnergyBasket.text = try {
            energyAmount!!.toInt()
        }catch (e : Exception){0}.toString()
        infoProteinsBasket.text =  try {
            fiberAmount!!.toInt()
        }catch (e : Exception){0}.toString()
        infoFatsBasket.text =  try {
            fatAmount!!.toInt()
        }catch (e : Exception){0}.toString()
        infoLayoutBasket.animate().alpha(1f).duration = 100
        RLBasket.visibility = View.VISIBLE
    }
}



