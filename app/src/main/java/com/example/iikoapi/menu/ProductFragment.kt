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
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.products_view_pager.view.*
import javax.inject.Inject

@AndroidEntryPoint
class ProductFragment: Fragment(R.layout.products_view_pager) {

    @Inject
    lateinit var viewModel: MainViewModel


    private var adapter: GeneralAdapter<Product> = ProductAdapter<Product>()
        .apply {
            setData(listOf())
            setLayoutId(R.layout.layout_for_product)
            setHlebListener(object : TabLayout.OnTabSelectedListener{
                override fun onTabReselected(tab: TabLayout.Tab?) {

                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    Log.d("tab", tab?.parent?.tag.toString())
                }

            })
            setListener(object : OnItemClickListener<Product> {
                override fun onClick(view: View, item: Product, position: Int?) {
                    when(view.id){
                        R.id.infoButton -> viewModel.showProductInfo(product = item)
                        R.id.backButton -> viewModel.back()
                    }
                }
            })
        }


    fun setProducts(products: List<Product>){
        adapter.setData(products)
        adapter.notifyDataSetChanged()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.productsViewPager.adapter = adapter

    }
}
