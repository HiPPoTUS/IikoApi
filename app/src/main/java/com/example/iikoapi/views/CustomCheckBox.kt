package com.example.iikoapi.views

import android.content.Context
import android.util.AttributeSet
import android.view.View.OnClickListener
import androidx.appcompat.widget.AppCompatImageView
import com.example.iikoapi.R

open class CustomCheckBox @JvmOverloads constructor(context: Context,
                                               attrs: AttributeSet? = null,
                                               defStyleAttr: Int = 0) : AppCompatImageView(context, attrs, defStyleAttr) {

    var checkedStateDrawable = R.drawable.ic_checkbox_checked

    var unCheckedStateDrawable = R.drawable.ic_checkbox_empty

    open var listener = OnClickListener {
        checked = !checked

    }

    set(value) {
        field = value
        setOnClickListener(value)
    }


    var checked = false
        set(value) {
            field = value
            if (checked) this.setImageResource(checkedStateDrawable)
            else this.setImageResource(unCheckedStateDrawable)
        }

}