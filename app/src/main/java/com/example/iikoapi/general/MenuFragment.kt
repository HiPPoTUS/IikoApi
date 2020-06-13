package com.example.iikoapi.general

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.iikoapi.R
import com.example.iikoapi.general.menuadapter.MenuAdapter
import com.example.iikoapi.general.merch.MerchAdapter
import com.example.iikoapi.utils.getCategories
import com.example.iikoapi.utils.getProdsByCategory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_menu.*

class MenuFragment(var position : Int) : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        val viewPager2 = view.findViewById<ViewPager2>(R.id.pager)
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        val merch = view.findViewById<ViewPager2>(R.id.merchViewPager)



//        viewPager2.reduceDragSensitivity()
//        viewPager2.orientation = ViewPager2.ORIENTATION_VERTICAL
//        viewPager2.adapter = MenuAdapter(dd.values.toList())

        merch.adapter = MerchAdapter(getProdsByCategory()[0])

        viewPager2.adapter = MenuAdapter(getProdsByCategory())
        viewPager2.setCurrentItem(position, false)


        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            //tab.text = tabs[position]
//            tab.text = dd.keys.elementAt(position)
            tab.text = getCategories().elementAt(position)
            tab.customView
        }.attach()

        return view
    }

    fun ViewPager2.reduceDragSensitivity() {
        val recyclerViewField = ViewPager2::class.java.getDeclaredField("mRecyclerView")
        recyclerViewField.isAccessible = true
        val recyclerView = recyclerViewField.get(this) as RecyclerView

        val touchSlopField = RecyclerView::class.java.getDeclaredField("mTouchSlop")
        touchSlopField.isAccessible = true
        val touchSlop = touchSlopField.get(recyclerView) as Int
        touchSlopField.set(recyclerView, touchSlop*8)       // "8" was obtained experimentally
    }

}
