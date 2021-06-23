@file:Suppress("UNCHECKED_CAST")

package com.example.iikoapi.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.iikoapi.R
import com.example.iikoapi.entities.GroupProducts
import com.example.iikoapi.entities.datatype.Image
import com.example.iikoapi.entities.datatype.Product
import com.google.android.material.tabs.TabLayout
import net.cachapa.expandablelayout.ExpandableLayout

object BindingAdapter {

    @JvmStatic
    @BindingAdapter(value = ["app:image"])
    fun setImage(view: ImageView, im: Image?){
        Glide
            .with(view.context)
            .load(im?.imageUrl)
            .placeholder(R.drawable.shav_placeholder)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter(value = ["app:image"])
    fun setImage(view: ImageView, url: String?){
        Glide
            .with(view.context)
            .load(url)
            .placeholder(R.drawable.shav_placeholder)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter(value = ["app:products", "app:onItemListener"], requireAll = true)
    fun setUpMenuRecyclerView(recyclerView: RecyclerView, products: GroupProducts, onItemListener: OnItemClickListener<*>){
        recyclerView.adapter = GeneralAdapter<Product>()
            .also { adapter ->
                adapter.setData(products.products)
                adapter.setLayoutId(R.layout.menu_product_item)
                adapter.setListener(onItemListener as OnItemClickListener<Product>)
            }
    }

    @JvmStatic
    @BindingAdapter("app:hleb", "app:hlebPriceTabLayout", "app:hlebPriceExpandableLayout")
    fun setUpHleb(tabLayout: TabLayout, x: Int, hlebPriceTabLayout: TabLayout, hlebPriceExpandableLayout: ExpandableLayout){

        tabLayout.removeAllTabs()
        hlebPriceTabLayout.removeAllTabs()
        hlebPriceExpandableLayout.collapse()

        for(i in 0 until x){
            tabLayout.addTab(tabLayout.newTab().setText("tab $i"))

            if(i == 1) {
                val tab = hlebPriceTabLayout.newTab()
                tab.text = "tab $i"
                hlebPriceTabLayout.addTab(tab)
                hlebPriceTabLayout.getTabAt(i)?.select()
            }
            else {
                hlebPriceTabLayout.addTab(hlebPriceTabLayout.newTab().setText(""))
            }

        }

        val touchableList = hlebPriceTabLayout.touchables
        touchableList?.forEach { it.isEnabled = false }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab?.text == "tab 1") hlebPriceExpandableLayout.expand()
                else hlebPriceExpandableLayout.collapse()
            }

        })
    }

}