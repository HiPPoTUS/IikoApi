package com.example.iikoapi.menu

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.iikoapi.R
import com.example.iikoapi.entities.MerchItem
import com.example.iikoapi.entities.datatype.Product
import com.example.iikoapi.main.MainViewModel
import com.example.iikoapi.utils.OnItemClickListener
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.products_view_pager.view.*
import javax.inject.Inject

@AndroidEntryPoint
class ProductsFragment(private val products: List<Product>, private val position: Int): Fragment(R.layout.products_view_pager) {

    @Inject
    lateinit var viewModel: MainViewModel

//    private lateinit var products: List<Product>
//
//    private lateinit var productsViewPager: ViewPager2
//
//    private val dobavitAdapter = ModifiersAdapter<MerchItem>().apply {
////            setListener(listener as OnItemClickListener<MerchItem>)
////            setWidth(recyclerView.width / 3 - 8.dp)
////            setData(data)
//        setLayoutId(R.layout.item_dobavit)
//    }

//    private var adapter = ProductAdapter<Product>()
//        .apply {
//            setData(listOf())
//            setLayoutId(R.layout.layout_for_product)
//            setDobavitAdapter(dobavitAdapter)
//            setListener(object : OnItemClickListener<Product> {
//                override fun onClick(view: View, item: Product, position: Int, tabLayout: TabLayout?) {
//                    when(view.id){
//                        R.id.infoButton -> viewModel.showProductInfo(product = item)
//                        R.id.backButton -> viewModel.back()
//                        R.id.addToBasketButton -> viewModel.addToOreder(products[position], tabLayout?.selectedTabPosition ?: 0, dobavitAdapter.getModifiers())
//                    }
//                }
//            })
//        }


//    fun setProducts(products: List<Product>, position: Int){
//        this.products = products
//        adapter.setData(products)
//        adapter.notifyDataSetChanged()
//        this.position = position
//    }
//
//    private var position = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.productsViewPager.adapter = ProductAdapter(this, products, viewModel)
        view.productsViewPager.setCurrentItem(position, false)

    }

}
