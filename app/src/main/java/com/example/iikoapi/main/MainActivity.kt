package com.example.iikoapi.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.iikoapi.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var factory : MainViewModelFactory
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        viewModel.setUpMainNavigation(supportFragmentManager, fragmentContainer)
        viewModel.setActivity(this)
        viewModel.setUpMapKit()

//        supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, StartFragment()).commit()

        viewModel.openMainFragment()


        viewModel.getMenu().observe(this, Observer{ menu ->
            if(menu != null){
                Log.d("tag", menu.toString())
            }
        })
    }
}