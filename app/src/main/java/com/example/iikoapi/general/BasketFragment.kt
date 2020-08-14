package com.example.iikoapi.general

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.iikoapi.R
import com.example.iikoapi.general.basketadapter.BasketRecycleViewAdapter
import com.example.iikoapi.openedmenuitem.order
import com.example.iikoapi.utils.Decorations
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.cloudpayments.sdk.cp_card.CPCard
import ru.cloudpayments.sdk.cp_card.api.CPCardApi


class BasketFragment(var contextMy: Context, var navView: BottomNavigationView, var payment: ConstraintLayout) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_basket, container, false)
        val api = CPCardApi(contextMy)
        val recyclerViewForBasket = view.findViewById<RecyclerView>(R.id.recycler_view_for_basket)
        val pay_go_to_menu = view.findViewById<Button>(R.id.pay_go_to_menu)
        val text_empty_basket = view.findViewById<TextView>(R.id.text_empty_basket)
        val bottomSheetBehavior = BottomSheetBehavior.from(payment)
        val cn_text = payment.findViewById<EditText>(R.id.edit_card_number)
        val cd_text = payment.findViewById<EditText>(R.id.edit_card_date)
        val cvc_text = payment.findViewById<EditText>(R.id.edit_card_cvc)
        val CN_Watcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (CPCard.isValidNumber(s.toString()))
                    cn_text.setBackgroundColor(Color.GREEN)
                else cn_text.setBackgroundColor(Color.RED)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        }
        cn_text.setOnFocusChangeListener(object : View.OnFocusChangeListener{
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if(!hasFocus&&!cn_text.text.isEmpty()&&CPCard.isValidNumber(cn_text.text.toString())) {
                    api.getBinInfo(cn_text.text.toString(), { binInfo ->
                        binInfo.bankName // Название банка
                        Glide.with(contextMy)
                            .load(binInfo.logoUrl)
                            .into(payment.findViewById<ImageView>(R.id.Banklogo))
                    }) { message -> Log.e("Error", message) }
                }
            }
        })
        val CD_Watcher = object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                if (CPCard.isValidExpDate(s.toString()))
                    cd_text.setBackgroundColor(Color.GREEN)
                else cd_text.setBackgroundColor(Color.RED)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
        }
        cd_text.addTextChangedListener(CD_Watcher)
        cn_text.addTextChangedListener(CN_Watcher)
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if(bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED)
                    hideKeyboard(contextMy as AppCompatActivity)
            }
        })
        payment.findViewById<Button>(R.id.check_pay).setOnClickListener {
            if (CPCard.isValidNumber(cn_text.text.toString())&&CPCard.isValidExpDate(cd_text.text.toString())){
                val card = CPCard(cn_text.text.toString(), cd_text.text.toString(), cvc_text.text.toString())
                val type = card.getType()
                val crypt = card.cardCryptogram("") // ASAAAAAAAAAAAAAAAAAAAAAAAAAAA!!!!!!!!!!!!!!!!!!!!tention!!!!!!!!!!!!!!!!
                Log.d("CRYPT",crypt)
                Log.d("TYPE",type)
            }

        }

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