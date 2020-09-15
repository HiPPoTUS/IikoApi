package com.example.iikoapi.general

import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.dodocopy.dataTypes.Address
import com.example.dodocopy.dataTypes.Customer
import com.example.iikoapi.R
import com.example.iikoapi.openedmenuitem.order
import com.example.iikoapi.startapp.datatype.*
import com.example.iikoapi.startapp.networking.AddressCheckResult
import com.example.iikoapi.startapp.networking.CP
import com.example.iikoapi.startapp.networking.Iiko
import com.example.iikoapi.startapp.networking.OrderChecksCreationResult
import com.example.iikoapi.startapp.streets
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.json.JSONObject
import ru.cloudpayments.sdk.cp_card.CPCard

class PaymentFragment(val contextMy: Activity){

    lateinit var total_price : TextView

    fun setCost(){
        total_price.text= order.fullSum.toString()
    }

    fun initViews(){
//        val inflater = LayoutInflater.from(contextMy)
        val view = contextMy

        val c_n = view.findViewById<EditText>(R.id.cu_name)
        val c_ph = view.findViewById<EditText>(R.id.telefon)
        val cn_text = view.findViewById<EditText>(R.id.edit_card_number)
        val cd_text = view.findViewById<EditText>(R.id.edit_card_date)
        val cvc_text = view.findViewById<EditText>(R.id.edit_card_cvc)
        val d = view.findViewById<EditText>(R.id.dom)
        val g = view.findViewById<TextView>(R.id.gorod)
        val str = view.findViewById<AutoCompleteTextView>(R.id.ulica)
        val chn_text = view.findViewById<EditText>(R.id.edit_card_holder_name)
        total_price = view.findViewById<TextView>(R.id.total_price)
        val check_pay = view.findViewById<Button>(R.id.check_pay)
        val check_order = view.findViewById<Button>(R.id.check_order)
        total_price.text=order.items.toString()
        str.setAdapter(ArrayAdapter<String>(view,R.layout.simple_dd_item_1line, Array(streets.streets!!.size){
            streets.streets!!.elementAt(it).name
        }))
        str.threshold = 2
//        str.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
//            if(hasFocus) str.showDropDown()
//        }

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

        check_order.setOnClickListener {
            val cu = Customer()
            cu.setThisName(c_n.text.toString())
            cu.setThisPhone(c_ph.text.toString())
            val ad = Address(g.text.toString(),str.text.toString(),d.text.toString())
            order.setThisAddress(ad)
            order.setThisPhone(c_ph.text.toString())

            val mapper = ObjectMapper()
            Log.d("adr",mapper.writeValueAsString(OrderRequest("05961721-b9bd-11e9-80e9-d8d38565926f",order,null,null)))
            val result  = CHECK((contextMy as GeneralActivity).iiko,OrderRequest("05961721-b9bd-11e9-80e9-d8d38565926f",order,null,null)).execute().get()
            Log.d("adr",result.toString())

        }
        check_pay.setOnClickListener {

            if (CPCard.isValidNumber(cn_text.text.toString()) && CPCard.isValidExpDate(cd_text.text.toString())) {
                val card = CPCard(
                    cn_text.text.toString(),
                    cd_text.text.toString(),
                    cvc_text.text.toString()
                )
                val crypt = card.cardCryptogram("pk_8f0f7ed76a09aead999effefde3ff")
                val req = PayRequestArgs(666, chn_text.text.toString(), crypt)
                val payment = Pay((contextMy as GeneralActivity).cp, req)
                val resp = payment.execute().get()
                if (resp.isSuccess()) {
                    Log.d("resp", resp.data.toString())
                    val trans = resp.data
                    if (trans?.paReq != null && trans.acsUrl != null) {
                        // Показываем 3DS форму
                        contextMy.show3DS(trans)
                    } else {
                        // Показываем результат
                        Toast.makeText(contextMy, trans?.cardHolderMessage, Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
    }
}

class CHECK(private var iiko : Iiko, val req: OrderRequest) : AsyncTask<Void, Void, OrderChecksCreationResult>() {
    override fun doInBackground(vararg params: Void?): OrderChecksCreationResult? {
        iiko.authentication()
        iiko.getOrganisation(0)
        return iiko.check_order(req)
    }
    override fun onPostExecute(result: OrderChecksCreationResult?) {
        super.onPostExecute(result)
    }
}

class Pay(var cp : CP, var req:PayRequestArgs) : AsyncTask<Void, Void, PayApiResponse<Transaction>>() {
    lateinit var resp: PayApiResponse<Transaction>
    override fun doInBackground(vararg params: Void?): PayApiResponse<Transaction>? {
        resp = cp.pay(req)
        return resp
    }
}

class Post3ds(var cp:CP, var req:Post3dsRequestArgs): AsyncTask<Void, Void, Transaction>(){
    lateinit var resp: PayApiResponse<Transaction>
    override fun doInBackground(vararg params: Void?): Transaction? {
        resp = cp.post3ds(req)
        return resp.data
    }
}