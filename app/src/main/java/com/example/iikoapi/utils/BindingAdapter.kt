@file:Suppress("UNCHECKED_CAST")

package com.example.iikoapi.utils

import android.graphics.Rect
import android.view.View
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.iikoapi.R
import com.example.iikoapi.entities.ChildRemove
import com.example.iikoapi.entities.ExpandableState
import com.example.iikoapi.entities.GroupProducts
import com.example.iikoapi.entities.datatype.Image
import com.example.iikoapi.entities.menu.Modifier
import com.example.iikoapi.entities.nomenclature.Product
import com.example.iikoapi.utils.adapters.CustomItemDecoration
import com.example.iikoapi.utils.adapters.GeneralAdapter
import com.example.iikoapi.views.CustomCheckBox
import com.example.iikoapi.views.CustomCheckBoxModifier
import com.google.android.material.tabs.TabLayout
import net.cachapa.expandablelayout.ExpandableLayout

object BindingAdapter {

    @JvmStatic
    @BindingAdapter(value = ["app:image"])
    fun setImage(view: ImageView, im: Image?) {
        Glide
            .with(view.context)
            .load(im?.imageUrl)
            .placeholder(R.drawable.shav_placeholder)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter(value = ["app:image"])
    fun setImage(view: ImageView, url: String?) {
        Glide
            .with(view.context)
            .load(url)
            .placeholder(R.drawable.shav_placeholder)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter(value = ["app:products", "app:onItemListener"], requireAll = true)
    fun setUpMenuRecyclerView(
        recyclerView: RecyclerView,
        products: GroupProducts,
        onItemListener: OnItemClickListener<*>
    ) {
        recyclerView.apply {
            if(itemDecorationCount == 0){
                addItemDecoration(CustomItemDecoration())
            }
        }.adapter = GeneralAdapter<Product>()
            .also { adapter ->
                adapter.setData(products.products)
                adapter.setLayoutId(R.layout.item_menu_product_item_new)
                adapter.setListener(onItemListener as OnItemClickListener<Product>)
            }
    }

    @JvmStatic
    @BindingAdapter(
        "app:hleb",
        "app:hlebPriceTabLayout",
        "app:hlebPriceExpandableLayout",
        "app:hlebLayoutBackground"
    )
    fun setUpHleb(
        tabLayout: TabLayout,
        hleb: List<Modifier>?,
        hlebPriceTabLayout: TabLayout,
        hlebPriceExpandableLayout: ExpandableLayout,
        hlebLayoutBackground: FrameLayout
    ) {

        if (hleb == null || hleb.isEmpty()) {
            hlebPriceExpandableLayout.isVisible = false
            hlebLayoutBackground.isVisible = false
            return
        }

        tabLayout.removeAllTabs()
        hlebPriceTabLayout.removeAllTabs()
        hlebPriceExpandableLayout.collapse()

        hleb.forEachIndexed { index, modifier ->
            tabLayout.addTab(tabLayout.newTab().setText(modifier.name))

            if (modifier.price.toInt() > 0) {
                val tab = hlebPriceTabLayout.newTab()
                tab.text = modifier.price + " ₽"
                hlebPriceTabLayout.addTab(tab)
                hlebPriceTabLayout.getTabAt(index)?.select()
            } else {
                hlebPriceTabLayout.addTab(hlebPriceTabLayout.newTab().setText(""))
            }
        }


        val touchableList = hlebPriceTabLayout.touchables
        touchableList?.forEach { it.isEnabled = false }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.text == "-ЛАВАШ СЫРНЫЙ") hlebPriceExpandableLayout.expand()
                else hlebPriceExpandableLayout.collapse()
            }

        })
    }

    @JvmStatic
    @BindingAdapter(value = ["app:arrow"])
    fun rotateExpandableArrow(view: ImageView, state: ExpandableState) {
        val angle = if (state == ExpandableState.Collapsed) 90f else 0f
        view.rotation = angle
    }

    @JvmStatic
    @BindingAdapter(value = ["app:double"])
    fun setUpDouble(view: TextView, double: Double) {
        view.text = double.toInt().toString()
    }

    @JvmStatic
    @BindingAdapter(value = ["app:listener", "app:item", "app:position", "app:checkBox"])
    fun setOnRemoveClickListener(view: ConstraintLayout, listener: OnItemClickListener<*>, item: ChildRemove, position: Int, checkBox: CustomCheckBoxModifier) {
        view.setOnClickListener {
            (listener as OnItemClickListener<ChildRemove>).onClick(view, item, position)
            checkBox.performClick()
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["app:removeModifiers"])
    fun addBasketDiscription(view: TextView, removeModifiers: List<Modifier>?) {
        var s = ""
        removeModifiers?.let {
            it.forEach {
                s += it.name
            }
        }
        view.text = s
    }

}