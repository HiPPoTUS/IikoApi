package com.example.iikoapi.startapp

import android.R
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment


class Dialog1 : DialogFragment(), DialogInterface.OnClickListener {
    val LOG_TAG = "myLogs"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dialog!!.setTitle("Error downloading data!")
        val v: View = inflater.inflate(R.layout.dialo, null)
        v.findViewById(R.id.btnYes).setOnClickListener(this)
        v.findViewById(R.id.btnNo).setOnClickListener(this)
        v.findViewById(R.id.btnMaybe).setOnClickListener(this)
        return v
    }

    override fun onClick(v: View) {
        Log.d(LOG_TAG, "Dialog 1: " + (v as Button).getText())
        dismiss()
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        Log.d(LOG_TAG, "Dialog 1: onDismiss")
    }

    override fun onCancel(dialog: DialogInterface?) {
        super.onCancel(dialog)
        Log.d(LOG_TAG, "Dialog 1: onCancel")
    }

    override fun onClick(p0: DialogInterface?, p1: Int) {
        TODO("Not yet implemented")
    }

}