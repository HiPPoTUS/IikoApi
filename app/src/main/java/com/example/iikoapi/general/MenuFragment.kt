package com.example.iikoapi.general

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.iikoapi.R
import com.example.iikoapi.general.menuadapter.MenuAdapter
import com.example.iikoapi.general.merch.MerchAdapter
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MenuFragment(var position : Int, var contextMy: Context) : Fragment() {

    private var height : Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        val viewPager2 = view.findViewById<ViewPager2>(R.id.pager)
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        val merch = view.findViewById<ViewPager2>(R.id.merchViewPager)


//        val merch = ViewPager2(contextMy)
//        val merchParams = CollapsingToolbarLayout.LayoutParams(CollapsingToolbarLayout.LayoutParams.MATCH_PARENT, (height * 0.15).toInt())
//        merchParams.collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PARALLAX
//        merchParams.parallaxMultiplier = 0.75f
//        merch.layoutParams = merchParams
//        collapsing.addView(merch)
        merch.adapter = MerchAdapter(mappedMenu.values.elementAt(0), contextMy)
        merch.offscreenPageLimit = mappedMenu.values.elementAt(0).size


        Log.d("tag", "fssdf - " + (height * 0.15).toInt())
//        val toolbar = Toolbar(contextMy)
//        val toolbarParams = CollapsingToolbarLayout.LayoutParams(CollapsingToolbarLayout.LayoutParams.MATCH_PARENT, 0)
//        toolbarParams.collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN
//        toolbarParams.gravity = Gravity.TOP
//        toolbarParams.setMargins(0, 0, 0, 55 + 24)
//        toolbar.popupTheme = android.R.style.ThemeOverlay_Material_Light
//        toolbar.layoutParams = toolbarParams
//        collapsing.addView(toolbar)
//
//
//
//        val tabLayout = TabLayout(contextMy)
//        val tabLayoutParams = CollapsingToolbarLayout.LayoutParams(CollapsingToolbarLayout.LayoutParams.MATCH_PARENT, CollapsingToolbarLayout.LayoutParams.WRAP_CONTENT)
//        tabLayout.setPadding(10, 0, 10, 0)
//        tabLayout.clipToPadding = false
//        tabLayout.setSelectedTabIndicator(R.drawable.selected_tab)
//        tabLayout.isTabIndicatorFullWidth = true
//        toolbarParams.gravity = Gravity.BOTTOM
////        tabLayout.tabIndicatorGravity = TabLayout.INDICATOR_GRAVITY_STRETCH
//        tabLayout.layoutParams = tabLayoutParams
//        collapsing.addView(tabLayout)


        viewPager2.adapter = MenuAdapter(mappedMenu.values.toList())
        viewPager2.adapter = MenuAdapter(mappedMenu.values.toList())
        viewPager2.setCurrentItem(position, false)
        viewPager2.offscreenPageLimit = mappedMenu.values.size


        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            //tab.text = tabs[position]
//            tab.text = dd.keys.elementAt(position)
            tab.text = mappedMenu.keys.elementAt(position)!!.name
            tab.customView
        }.attach()

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val display: Display = activity!!.getWindowManager().getDefaultDisplay()
        val size = Point()
        display.getSize(size)
        val width = size.x
        height= size.y

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
