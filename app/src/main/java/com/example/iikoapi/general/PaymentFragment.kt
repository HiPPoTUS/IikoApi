package com.example.iikoapi.general

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.iikoapi.R
import com.example.iikoapi.startapp.datatype.PayRequestArgs
import com.example.iikoapi.startapp.datatype.Post3dsRequestArgs
import com.example.iikoapi.startapp.networking.CP
import com.example.iikoapi.startapp.networking.Iiko
import com.example.iikoapi.startapp.networking.CpNetworkService
import com.example.iikoapi.startapp.networking.IikoNetworkService
import kotlinx.android.synthetic.main.on_order_pop_up.*
import ru.cloudpayments.sdk.cp_card.CPCard
import ru.cloudpayments.sdk.cp_card.api.CPCardApi
import ru.cloudpayments.sdk.three_ds.ThreeDSDialogListener

class PaymentFragment(val contextMy: Context) : Fragment(), ThreeDSDialogListener {

    val cp = CP(CpNetworkService.instance!!)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.on_order_pop_up, container, false)

        val cn_text = view.findViewById<EditText>(R.id.edit_card_number)
        val cd_text = view.findViewById<EditText>(R.id.edit_card_date)
        val cvc_text = view.findViewById<EditText>(R.id.edit_card_cvc)
        val contacts = view.findViewById<LinearLayout>(R.id.cu_contacts)
        val addr = view.findViewById<LinearLayout>(R.id.cu_address)
        val chn_text = view.findViewById<EditText>(R.id.edit_card_holder_name)

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

        payment.findViewById<Button>(R.id.check_pay).setOnClickListener {
            if (CPCard.isValidNumber(cn_text.text.toString()) && CPCard.isValidExpDate(cd_text.text.toString())) {
                val card = CPCard(
                    cn_text.text.toString(),
                    cd_text.text.toString(),
                    cvc_text.text.toString()
                )
                val crypt = card.cardCryptogram("pk_8f0f7ed76a09aead999effefde3ff")
                val req = PayRequestArgs(505, chn_text.text.toString(), crypt)
                val payment = Pay((activity as GeneralActivity).cp, req)
                val resp = payment.execute().get()
                if (resp.isSuccess()) {
                    Log.d("resp", resp.data.toString())
                    val trans = resp.data
                    if (trans?.paReq != null && trans.acsUrl != null) {
                        // Показываем 3DS форму
                        (activity as GeneralActivity).show3DS(trans);
                    } else {
                        // Показываем результат
                        Toast.makeText(contextMy, trans?.cardHolderMessage, Toast.LENGTH_LONG)
                            .show();
                    }
                }
            }
        }

        return view
    }

    override fun onAuthorizationCompleted(md: String?, paRes: String?) {
        Post3ds(cp, Post3dsRequestArgs(md, paRes))
    }

    override fun onAuthorizationFailed(html: String?) {
        Toast.makeText(contextMy, "AuthorizationFailed: $html",Toast.LENGTH_LONG).show();
    }
}