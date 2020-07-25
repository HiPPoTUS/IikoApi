package com.example.iikoapi.openedmenuitem

import Product
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.iikoapi.R
import com.example.iikoapi.general.menu_prods
import kotlinx.android.synthetic.main.layout_opened_product_item_view_pager2.*

class OpenedMenuItem : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_opened_product_item_view_pager2)

        val position = intent.getIntExtra("position", 0)
        val commonPos = intent.getIntExtra("comonPos", -1)
//        val openedMenuItemAdapter = OpenedMenuItemAdapter(menu_prods.values.elementAt(commonPos), this, commonPos)
//        opened_menu_item_pager.adapter = openedMenuItemAdapter
//        opened_menu_item_pager.setCurrentItem(position, false)
//        opened_menu_item_pager.offscreenPageLimit = menu_prods.values.elementAt(commonPos).size

        val pagerAdapter = ScreenSlidePagerAdapter(this, menu_prods.values.elementAt(commonPos).size, menu_prods.values.elementAt(commonPos), commonPos)
        opened_menu_item_pager.adapter = pagerAdapter
        opened_menu_item_pager.setCurrentItem(position, false)
        opened_menu_item_pager.offscreenPageLimit = menu_prods.values.elementAt(commonPos).size

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.enter_anim_left, R.anim.exit_anim_left)
    }
}

private class ScreenSlidePagerAdapter(var fa: FragmentActivity, var count : Int, var products: List<Product>, var commonPosition : Int) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = count

    override fun createFragment(position: Int): Fragment = OpenItemFragment(fa, products[position], commonPosition)
}