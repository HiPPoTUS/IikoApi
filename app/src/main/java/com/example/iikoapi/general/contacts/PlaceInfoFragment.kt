package com.example.iikoapi.general.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.iikoapi.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class PlaceInfoFragment: BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.place_info_bottom_sheet, container,
            false
        )
    }
}