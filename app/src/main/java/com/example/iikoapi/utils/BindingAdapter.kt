package com.example.iikoapi.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.iikoapi.R
import com.example.iikoapi.entities.GroupProducts
import com.example.iikoapi.entities.MerchItem
import com.example.iikoapi.entities.datatype.Image
import com.example.iikoapi.entities.datatype.Product

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

}