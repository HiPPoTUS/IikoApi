package com.example.iikoapi.basket

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.fragment.app.Fragment
import com.example.iikoapi.R
import com.example.iikoapi.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_basket.view.*
import javax.inject.Inject

//@AndroidEntryPoint
class BasketFragment : Fragment(R.layout.fragment_basket){

//    @Inject
//    lateinit var viewModel: MainViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        foo(3)
        view.payGoToMenu

    }

    companion object {
        const val TAG = 4
    }

    val INDEX_TAG get() = TAG

    fun <T> foo(t: T){
        print(7)
    }
}