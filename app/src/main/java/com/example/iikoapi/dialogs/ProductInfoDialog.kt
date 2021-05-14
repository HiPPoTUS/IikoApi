package com.example.iikoapi.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.example.iikoapi.R
import com.example.iikoapi.databinding.DialogProductInfoBinding
import com.example.iikoapi.entities.datatype.Product


class ProductInfoDialog(private val product: Product): DialogFragment(R.layout.dialog_product_info) {

    private var binding: DialogProductInfoBinding? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    override fun onStart() {
        super.onStart()
        val window: Window? = dialog!!.window
        val windowParams = window?.attributes
        windowParams?.dimAmount = 0f
        window?.attributes = windowParams
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DialogProductInfoBinding.bind(view)
        binding?.product = product
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }


    companion object {
        const val TAG = "ProductInfoDialog"
    }

}