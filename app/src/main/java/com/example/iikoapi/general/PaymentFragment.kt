package com.example.iikoapi.general

import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.iikoapi.R
import com.example.iikoapi.startapp.datatype.PayApiResponse
import com.example.iikoapi.startapp.datatype.PayRequestArgs
import com.example.iikoapi.startapp.datatype.Post3dsRequestArgs
import com.example.iikoapi.startapp.datatype.Transaction
import com.example.iikoapi.startapp.networking.CP
import com.example.iikoapi.startapp.networking.Iiko
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.cloudpayments.sdk.cp_card.CPCard

class PaymentFragment(val contextMy: Activity){

    fun initViews(){
//        val inflater = LayoutInflater.from(contextMy)
        val view = contextMy

        val cn_text = view.findViewById<EditText>(R.id.edit_card_number)
        val cd_text = view.findViewById<EditText>(R.id.edit_card_date)
        val cvc_text = view.findViewById<EditText>(R.id.edit_card_cvc)
        val contacts = view.findViewById<LinearLayout>(R.id.cu_contacts)
        val addr = view.findViewById<LinearLayout>(R.id.cu_address)
        val chn_text = view.findViewById<EditText>(R.id.edit_card_holder_name)
        val total_price = view.findViewById<TextView>(R.id.total_price)
        val check_pay = view.findViewById<Button>(R.id.check_pay)

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

class Dat(private var iiko : Iiko, var context: Context, private var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>) : AsyncTask<Void, Void, Void>() {
    override fun doInBackground(vararg params: Void?): Void? {
        iiko.authentication()
        iiko.getOrganisation(0)
        return null
    }
    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
        if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
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