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
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.iikoapi.R
import com.example.iikoapi.startapp.networking.Iiko
import com.example.iikoapi.startapp.networking.NetworkService
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.on_order_pop_up.*
import ru.cloudpayments.sdk.cp_card.CPCard
import ru.cloudpayments.sdk.cp_card.api.CPCardApi

class PaymentFragment(val contextMy: Context) : Fragment() {

    val api = CPCardApi(contextMy)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.on_order_pop_up, container, false)

        val cn_text = view.findViewById<EditText>(R.id.edit_card_number)
        val cd_text = view.findViewById<EditText>(R.id.edit_card_date)
        val cvc_text = view.findViewById<EditText>(R.id.edit_card_cvc)
        val contacts = view.findViewById<LinearLayout>(R.id.cu_contacts)
        val addr = view.findViewById<LinearLayout>(R.id.cu_address)
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
        cn_text.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if(!hasFocus&& cn_text.text.isNotEmpty() && CPCard.isValidNumber(cn_text.text.toString())) {
                api.getBinInfo(cn_text.text.toString(), { binInfo ->
                    binInfo.bankName // Название банка
                    Glide.with(contextMy)
                        .load(binInfo.logoUrl)
                        .into(payment.findViewById(R.id.Banklogo))
                }) { message -> Log.e("Error", message) }
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
            if (CPCard.isValidNumber(cn_text.text.toString())&& CPCard.isValidExpDate(cd_text.text.toString())){
                val card = CPCard(cn_text.text.toString(), cd_text.text.toString(), cvc_text.text.toString())
                val type = card.type
                val crypt = card.cardCryptogram("") // ASAAAAAAAAAAAAAAAAAAAAAAAAAAA!!!!!!!!!!!!!!!!!!!!tention!!!!!!!!!!!!!!!!
                Log.d("CRYPT",crypt)
                Log.d("TYPE",type)
            }

        }

        return view
    }
}