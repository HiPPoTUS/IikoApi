package com.example.iikoapi.general.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.iikoapi.R
import com.example.iikoapi.entities.District
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.place_info_bottom_sheet.view.*


class PlaceInfoFragment: BottomSheetDialogFragment() {

    private var district: District? = null

    fun setData(district: District){
        this.district = district
    }

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.place_info_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.textDistrict.text = district?.adr
    }
}