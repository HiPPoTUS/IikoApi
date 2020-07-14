package com.example.iikoapi.startapp

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.iikoapi.R


class DialogFragmentErrorSavedata(private val startActivityContext : Context, private val problem : Boolean, private val progressBar: ProgressBar)
    : DialogFragment(){
    val LOG_TAG = "myLogs"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dialog!!.setTitle("Error downloading data!")
        val v: View = inflater.inflate(R.layout.dialog_fragment_error_savedata, null)
        progressBar.visibility = View.GONE
        when(problem){
            false -> v.findViewById<TextView>(R.id.error_text).text = "internet"
            true -> v.findViewById<TextView>(R.id.error_text).text = "Iiko"
        }
        v.findViewById<Button>(R.id.try_again_button).setOnClickListener {
            startActivityContext.startActivity(Intent(startActivityContext, StartActivity::class.java))
        }

        v.findViewById<Button>(R.id.close_app_button).setOnClickListener {
            activity!!.finish()
            System.exit(0)
        }

        return v
    }

}