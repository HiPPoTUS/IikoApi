package com.example.iikoapi.start

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.iikoapi.R
import com.example.iikoapi.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StartFragment: Fragment(R.layout.activity_start) {

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}