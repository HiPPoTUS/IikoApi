package com.example.iikoapi.general.payment

import android.app.Activity
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.iikoapi.R
import ru.cloudpayments.sdk.cp_card.CPCard
import ru.cloudpayments.sdk.three_ds.ThreeDSDialogListener
import ru.cloudpayments.sdk.three_ds.ThreeDsDialogFragment

class CheckoutPayment(val context: Activity) : ThreeDSDialogListener{

    private var totalPrice = context.findViewById<TextView>(R.id.total_price)
    private var editCardNumber = context.findViewById<EditText>(R.id.edit_card_number)
    private var editCardDate = context.findViewById<EditText>(R.id.edit_card_date)
    private var editCardCVC = context.findViewById<EditText>(R.id.edit_card_cvc)
    private var editCardHolderName = context.findViewById<EditText>(R.id.edit_card_holder_name)
    private var checkPayment = context.findViewById<Button>(R.id.check_pay)
    private lateinit var cardNumber : String
    private lateinit var cardDate : String
    private lateinit var cardCVC : String
    private lateinit var cardType : String
    private lateinit var bankName : String


    fun init(){
//        cardType = CPCard(cardNumber, cardDate, cardCVC).type
//        val api = CPCardApi(context)
        // GOOGLE PAY

        // It's recommended to create the PaymentsClient object inside of the onCreate method.
        checkPayment.setOnClickListener {
            if(checkCredentials()){
                val card = CPCard(cardNumber, cardDate, cardCVC);
                card.cardCryptogram(context.resources.getString(R.string.publicId))
//                ThreeDsDialogFragment.newInstance(transaction.getAcsUrl(),
//                    String transactionId,
//                    String paReq)
//                    .show(getSupportFragmentManager(), "3DS");
            }
        }
    }

    private fun checkCredentials() : Boolean = CPCard.isValidNumber(cardNumber) && CPCard.isValidExpDate(cardDate)

    override fun onAuthorizationCompleted(md: String?, paRes: String?) {
        TODO("Not yet implemented")
    }

    override fun onAuthorizationFailed(html: String?) {
        TODO("Not yet implemented")
    }

    companion object{
        private const val CARD_NUMBER_TOTAL_SYMBOLS = 19 // size of pattern 0000-0000-0000-0000

        private const val CARD_NUMBER_TOTAL_DIGITS =
            16 // max numbers of digits in pattern: 0000 x 4

        private const val CARD_NUMBER_DIVIDER_MODULO =
            5 // means divider position is every 5th symbol beginning with 1

        private const val CARD_NUMBER_DIVIDER_POSITION =
            CARD_NUMBER_DIVIDER_MODULO - 1 // means divider position is every 4th symbol beginning with 0

        private const val CARD_NUMBER_DIVIDER = ' '

        private const val CARD_DATE_TOTAL_SYMBOLS = 5 // size of pattern MM/YY

        private const val CARD_DATE_TOTAL_DIGITS = 4 // max numbers of digits in pattern: MM + YY

        private const val CARD_DATE_DIVIDER_MODULO =
            3 // means divider position is every 3rd symbol beginning with 1

        private const val CARD_DATE_DIVIDER_POSITION =
            CARD_DATE_DIVIDER_MODULO - 1 // means divider position is every 2nd symbol beginning with 0

        private const val CARD_DATE_DIVIDER = '/'

        private const val CARD_CVC_TOTAL_SYMBOLS = 3
    }
}