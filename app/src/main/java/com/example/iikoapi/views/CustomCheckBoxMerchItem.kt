package com.example.iikoapi.views

import android.content.Context
import android.util.AttributeSet
import com.example.iikoapi.entities.MerchItem

class CustomCheckBoxMerchItem @JvmOverloads constructor(context: Context,
                                                         attrs: AttributeSet? = null,
                                                         defStyleAttr: Int = 0): CustomCheckBox(context, attrs, defStyleAttr) {

    override var listener = OnClickListener {
        checked = !checked
        product.isSelected = !(product.isSelected)

    }

        set(value) {
            field = value
            setOnClickListener(value)
        }

    lateinit var product: MerchItem

    init {
        setImageResource(unCheckedStateDrawable)
        setOnClickListener(listener)
    }

}