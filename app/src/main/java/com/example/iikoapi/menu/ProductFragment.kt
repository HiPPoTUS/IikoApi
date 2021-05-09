package com.example.iikoapi.menu

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.iikoapi.R
import com.example.iikoapi.entities.datatype.Product
import com.example.iikoapi.main.MainViewModel
import com.example.iikoapi.utils.GeneralAdapter
import com.example.iikoapi.utils.OnItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.products_view_pager.view.*
import javax.inject.Inject

@AndroidEntryPoint
class ProductFragment: Fragment(R.layout.products_view_pager) {

    private var adapter: GeneralAdapter<Product> = GeneralAdapter<Product>()
        .apply {
            setData(listOf())
            setLayoutId(R.layout.opened_item_for_view_pager)
            setListener(object : OnItemClickListener<Product> {
                override fun onClick(view: View, item: Product, position: Int?) {
                    Log.d("click", "product")
                }
            })
        }


    fun setProducts(products: List<Product>){
        adapter.setData(products)
        adapter.notifyDataSetChanged()
    }

    @Inject
    lateinit var viewModel: MainViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.productsViewPager.adapter = adapter

    }
}