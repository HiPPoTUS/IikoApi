package com.example.iikoapi.openedmenuitem

import Product
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.children
import androidx.core.view.forEach
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.iikoapi.R
import com.example.iikoapi.general.GeneralActivity
import com.example.iikoapi.general.mods_prods
import com.example.iikoapi.startapp.datatype.OrderItem
import com.example.iikoapi.startapp.datatype.OrderItemModifier
import com.example.iikoapi.utils.setBadges
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_menu.*
import kotlinx.android.synthetic.main.modifier_item.view.*
import kotlinx.android.synthetic.main.opened_item_for_view_pager.view.*


class OpenItemFragment (private val contextMy: Context, var product: Product, var commonPosition : Int) : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.opened_item_for_view_pager, container, false)

        bind(product, 0, view)
        return view
    }
    fun bind(currentItem : Product, position: Int, myView : View) {
        val img = myView.findViewById<ImageView>(R.id.image_inside)
        val contains = myView.findViewById<TextView>(R.id.contains)
        val weightInfo = myView.findViewById<TextView>(R.id.weightInfo)
        val scrollView = myView.findViewById<NestedScrollView>(R.id.whole_lt)

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)

        Glide.with(contextMy)
            .applyDefaultRequestOptions(requestOptions)
            .load(try{currentItem.images[0].imageUrl}catch (e:Exception){"dd"})
            .into(img)

        myView.collapsing_layout.title = currentItem.name
        contains.text = currentItem.description
        weightInfo.text = "г ${(currentItem.weight!!*1000).toInt()}"

        myView.go_back.setOnClickListener {
            val intent = Intent(contextMy, GeneralActivity::class.java)
            intent.putExtra("back_from", commonPosition)
            contextMy.startActivity(intent, ActivityOptions.makeCustomAnimation(contextMy, R.anim.enter_anim_left, R.anim.exit_anim_left).toBundle())
        }



        var hleb= emptyList<Product>()
        var bez= emptyList<Product>()
        var jalapenos= emptyList<Product>()
        if (currentItem.seoText=="1")
            jalapenos = mods_prods.values.elementAt(0)
        currentItem.groupModifiers!!.forEach {
            if (it.modifierID == Ghleb[1]) hleb = mods_prods[it.modifierID]!!
            if (it.modifierID == GhlebPita[1]) hleb = mods_prods[it.modifierID]!!
            if (it.modifierID == Gbez[1]) bez = mods_prods[it.modifierID]!!
        }

        if (bez.isEmpty()) myView.bez_group.visibility=View.GONE else showModifiers(bez, myView.bez_modifier, contextMy, "BEZ")
        if (jalapenos.isEmpty()) myView.with_group.visibility=View.GONE else showModifiers(jalapenos, myView.singled_modifier, contextMy, "DOBAVIT")
        if (hleb.isEmpty()) {myView.back_of_the_hleb.visibility=View.GONE
            myView.group_modifier.visibility=View.GONE}
        else {
            showGroupModifier(hleb, myView.group_modifier, contextMy, myView.tab_price)
        }

        myView.price_for_lavash.collapse()
        myView.group_modifier.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(hleb[tab!!.position]!!.price != 0) myView.price_for_lavash.expand()
                else myView.price_for_lavash.collapse()
            }
        })


        myView.RL.layoutParams = (RelativeLayout.LayoutParams(0,0))
        myView.RL.setOnTouchListener { _, _ ->
            myView.info_fragment_RL.animate().alpha(0f).duration = 100
            myView.RL.layoutParams = (RelativeLayout.LayoutParams(0,0))
            true
        }

        myView.info_button.setOnClickListener {
            showInfo(myView.info_fragment_RL)
            myView.info_fragment_RL.animate().alpha(1f).duration = 100
            myView.RL.layoutParams = (RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT))
        }

        myView.bez_button.setOnClickListener {
            myView.expandableLayout.toggle()
            scrollView.postDelayed({
                scrollView.smoothScrollTo(0, getDistanceBetweenViews(scrollView, myView.bez_button))
            }, 250)
        }

        myView.to_basket_button.setOnClickListener {
            val orderItem = OrderItem().fromProduct(currentItem)
            if (jalapenos.isNotEmpty())
                orderItem.modifiers.add(OrderItemModifier().fromProduct(jalapenos[myView.group_modifier.selectedTabPosition]))
            if (hleb.isNotEmpty())
                orderItem.modifiers.add(OrderItemModifier().fromProduct(hleb[myView.group_modifier.selectedTabPosition]))
            if (bez.isNotEmpty())
                myView.bez_modifier.children.forEach {
                if (it.check_box.isChecked) orderItem.modifiers.add(OrderItemModifier().fromProduct(bez[myView.bez_modifier.indexOfChild(it)]))
                }
            order.addToOrder(orderItem)
            val toast = Toast.makeText(contextMy, "Добавлено в корзину", Toast.LENGTH_SHORT)
            toast.view.background.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN)
            toast.view.findViewById<TextView>(android.R.id.message).setTextColor(Color.WHITE)
            toast.show()
            setBadges()
        }
        myView.expandableLayout.collapse()
    }

    private fun getDistanceBetweenViews(firstView: View, secondView: View): Int {
        val firstPosition = IntArray(2)
        val secondPosition = IntArray(2)
        firstView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        firstView.getLocationOnScreen(firstPosition)
        secondView.getLocationOnScreen(secondPosition)
        val b = firstView.measuredHeight + firstPosition[1]
        val t = secondPosition[1]
        return Math.abs(b - t)
    }


    private fun showInfo(view: RelativeLayout){

        view.info_name.text = product.name
        view.info_carbohydrates.text = try {
            product.carbohydrateAmount!!.toInt()
        }catch (e : Exception){}.toString()
        view.info_energy.text = try {
            product.energyAmount!!.toInt()
        }catch (e : Exception){0}.toString()
        view.info_proteins.text =  try {
            product.fiberAmount!!.toInt()
        }catch (e : Exception){0}.toString()
        view.info_fats.text =  try {
            product.fatAmount!!.toInt()
        }catch (e : Exception){0}.toString()
    }

    private fun showGroupModifier(data : List<Product>, lavashLayout : TabLayout, context: Context, tabLayout: TabLayout){
        var index = -1
        for(x in data.indices){
            lavashLayout.addTab(lavashLayout.newTab().setText(data[x].name))
            if(data[x].price != 0) {
                index = x
                val tab = tabLayout.newTab()
                tab.text = data[x].price.toString()
                tabLayout.addTab(tab)
            }
            else tabLayout.addTab(tabLayout.newTab().setText(""))
        }

        val tab = tabLayout.getTabAt(index)
        tab!!.select()
        val touchableList = tabLayout.touchables
        touchableList?.forEach { it.isEnabled = false }

    }

    private fun showModifiers(data : List<Product>, tableLayout : TableLayout, context: Context, type : String){

        val COLUMNS = 3
        val ROWS = data.size / COLUMNS
        val EXTRA_ITEMS = data.size - ROWS * COLUMNS


        for (i in 0 until ROWS) {
            val tableRow = TableRow(context)
            tableRow.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)
            for (j in 0 until COLUMNS) {

                tableRow.addView(createModifierView(context, data[i * 3 + j], i * 3 + j, type))
            }
            tableLayout.addView(tableRow, i)
        }

        val tableRow = TableRow(context)
        tableRow.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)
        for(i in 0 until EXTRA_ITEMS){
            tableRow.addView(createModifierView(context, data[ROWS * COLUMNS + i], ROWS * COLUMNS + i, type))
        }
        if(EXTRA_ITEMS != 0) {
            val spaceParam = TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            for(i in 0 until COLUMNS - EXTRA_ITEMS){
                val space = Space(context)
                space.layoutParams = spaceParam
                tableRow.addView(space)
            }
        }
        tableLayout.addView(tableRow)
    }

    private fun createModifierView(context: Context, product: Product, currentPosition : Int, type : String) : LinearLayout {

        val inflater = LayoutInflater.from(context)
        val parent = inflater!!.inflate(R.layout.modifier_item, null) as LinearLayout
        val linearLayoutParams = TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        parent.layoutParams = linearLayoutParams

        val requestOptions = RequestOptions().placeholder(R.drawable.preload).error(R.drawable.preload)
        Glide
            .with(parent.modifier_img.context)
            .applyDefaultRequestOptions(requestOptions)
            .load(try{product.images[0].imageUrl}catch (e:Exception){"dd"})
            .into(parent.modifier_img)

        parent.modifier_name.text = product.name

        if(type == "DOBAVIT")
            parent.modifier_price.text = product.price.toString()
        if(type == "BEZ")
            parent.empty_text.visibility = View.GONE


        parent.setOnClickListener {
            parent.check_box.toggle()
            if(parent.check_box.isChecked){
                parent.is_checked.setImageResource(R.drawable.ic_contacts_black_24dp)
            }
            else{
                parent.is_checked.setImageDrawable(null)
            }
        }
        return parent
    }
}