package com.example.iikoapi.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.iikoapi.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.view.*
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment: Fragment(R.layout.fragment_main) {

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setUpNavigationFragments(view.mainContainer.id)

        view.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            viewModel.changeFragment(menuItem.itemId)
        }
    }

    companion object{
        const val TAG = "MAIN_FRAGMENT"
    }
}