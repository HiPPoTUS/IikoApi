package com.example.iikoapi.openedmenuitem

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.iikoapi.R
import com.example.iikoapi.general.mappedMenu
import kotlinx.android.synthetic.main.layout_opened_product_item_view_pager2.*

class OpenedMenuItem : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_opened_product_item_view_pager2)

        val position = intent.getIntExtra("position", 0)


        val commonPos = intent.getIntExtra("comonPos", -1)
        val openedMenuItemAdapter = OpenedMenuItemAdapter(mappedMenu.values.elementAt(commonPos), this, commonPos)
        opened_menu_item_pager.adapter = openedMenuItemAdapter
        opened_menu_item_pager.setCurrentItem(position, false)
        opened_menu_item_pager.offscreenPageLimit = mappedMenu.values.elementAt(commonPos).size

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.enter_anim_left, R.anim.exit_anim_left)
    }
}