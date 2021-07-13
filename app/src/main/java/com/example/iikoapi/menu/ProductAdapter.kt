package com.example.iikoapi.menu

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.iikoapi.entities.nomenclature.Product
import com.example.iikoapi.main.MainViewModel
import com.example.iikoapi.menu.openedProduct.OpenProductFragment


class ProductAdapter(fragment: Fragment, private val products: List<Product>, private val viewModel: MainViewModel): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = products.size

    override fun createFragment(position: Int): Fragment =
        OpenProductFragment(
            products[position],
            viewModel
        )
}
