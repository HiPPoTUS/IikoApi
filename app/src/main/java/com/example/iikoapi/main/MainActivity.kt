package com.example.iikoapi.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.iikoapi.R
import com.example.iikoapi.utils.LoadingState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        viewModel.supportFragmentManager = supportFragmentManager
        viewModel.activity = this
        viewModel.setUpMapKit()

    }

}