package com.example.iikoapi.general

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.dodocopy.dataTypes.Address
import com.example.iikoapi.R
import com.example.iikoapi.general.basketadapter.BasketRecycleViewAdapter
import com.example.iikoapi.openedmenuitem.order
import com.example.iikoapi.startapp.datatype.PayApiResponse
import com.example.iikoapi.startapp.datatype.PayRequestArgs
import com.example.iikoapi.startapp.datatype.Post3dsRequestArgs
import com.example.iikoapi.startapp.datatype.Transaction
import com.example.iikoapi.startapp.menu
import com.example.iikoapi.startapp.networking.CP
import com.example.iikoapi.startapp.networking.Iiko
import com.example.iikoapi.startapp.networking.cp_NetworkService
import com.example.iikoapi.startapp.networking.iiko_NetworkService
import com.example.iikoapi.utils.Decorations
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.cloudpayments.sdk.cp_card.CPCard
import ru.cloudpayments.sdk.cp_card.api.CPCardApi


class BasketFragment(var contextMy: Context, var navView: BottomNavigationView, var payment: ConstraintLayout) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val iiko = Iiko(contextMy,provider = iiko_NetworkService.instance!!,pb = null)
        val cp = CP(cp_NetworkService.instance!!)
        val view = inflater.inflate(R.layout.fragment_basket, container, false)
        val api = CPCardApi(contextMy)
        val recyclerViewForBasket = view.findViewById<RecyclerView>(R.id.recycler_view_for_basket)
        val pay_go_to_menu = view.findViewById<Button>(R.id.pay_go_to_menu)
        val text_empty_basket = view.findViewById<TextView>(R.id.text_empty_basket)
        val bottomSheetBehavior = BottomSheetBehavior.from(payment)
        val cn_text = payment.findViewById<EditText>(R.id.edit_card_number)
        val cd_text = payment.findViewById<EditText>(R.id.edit_card_date)
        val cvc_text = payment.findViewById<EditText>(R.id.edit_card_cvc)
        val chn_text = payment.findViewById<EditText>(R.id.edit_card_holder_name)
        val contacts = payment.findViewById<LinearLayout>(R.id.cu_contacts)
        val addr = payment.findViewById<LinearLayout>(R.id.cu_address)
        val CN_Watcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (CPCard.isValidNumber(s.toString()))
                    cn_text.backgroundTintList = contextMy.getColorStateList(R.color.yellow)
                else cn_text.backgroundTintList = contextMy.getColorStateList(R.color.orange)
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
                    cd_text.backgroundTintList = contextMy.getColorStateList(R.color.yellow)
                else cd_text.backgroundTintList = contextMy.getColorStateList(R.color.orange)
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
                val crypt = card.cardCryptogram("test_api_00000000000000000000002") // ASAAAAAAAAAAAAAAAAAAAAAAAAAAA!!!!!!!!!!!!!!!!!!!!tention!!!!!!!!!!!!!!!!
                val req = PayRequestArgs(10,chn_text.text.toString() ,crypt)
                val payment = pay(cp,req)
                payment.execute()
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

class pay(var cp : CP, var req:PayRequestArgs) : AsyncTask<Void, Void, Void>() {
    lateinit var resp:PayApiResponse<Transaction>
    override fun doInBackground(vararg params: Void?): Void? {
        resp = cp.pay(req)
        return null
    }
    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
        if (resp.isSuccess()){
            if(!resp.success)
            {
                val req3ds = Post3dsRequestArgs(resp.data!!.id,resp.data!!.paReq)
                val sd3 = post3ds(cp,req3ds)
                sd3.execute()
            }
        }
    }
}

class post3ds(var cp:CP,var req:Post3dsRequestArgs): AsyncTask<Void,Void,Void>(){
    lateinit var resp:PayApiResponse<Transaction>
    override fun doInBackground(vararg params: Void?): Void? {
        resp = cp.post3ds(req)
        return null
    }
    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
        if (resp.isSuccess())
        {
            Log.d("EEEEEEEEEEEE!!!3DS!!!!",resp.data!!.cardHolderMessage)
        }
    }
}