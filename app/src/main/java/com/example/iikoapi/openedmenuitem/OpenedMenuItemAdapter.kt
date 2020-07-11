package com.example.iikoapi.openedmenuitem

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.LinearLayout
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.iikoapi.R
import com.example.iikoapi.general.GeneralActivity
import com.example.iikoapi.startapp.datatype.Order
import com.example.iikoapi.startapp.datatype.OrderItem
import com.example.iikoapi.startapp.datatype.OrderItemModifier
import com.example.iikoapi.startapp.datatype.Product
import com.example.iikoapi.startapp.networking.menu
import com.example.iikoapi.utils.setBadges
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.modifier_item.view.*
import kotlinx.android.synthetic.main.opened_item_for_view_pager.view.*


var order = Order()
var hlebGroupName = listOf<String?>("-лаваши без питы","840b1dc1-d18e-4cfb-8250-fd86eb59b9ea")
var hlebPitaGroupName = listOf<String?>("-лаваши с питой","1fc607d6-5075-446a-bdea-58db6b2c5a29")
var bezGroupName = listOf<String?>("---БЕЗ---","932f004d-0b8a-44c5-b8f7-8f86056e8d93")
var jalapenoGroupName = listOf<String?>(null,null)

class OpenedMenuItemAdapter(private var items : List<Product>, private var context: Context, var commonPosition : Int) : RecyclerView.Adapter<OpenedMenuItemAdapter.OpenedMenuItemViewHolder>() {

    private lateinit var hlebModifiers: MutableList<OrderItemModifier>
    private lateinit var jalapenoModifiers : MutableList<OrderItemModifier>
    private lateinit var bezModifiers : MutableList<OrderItemModifier>

    inner class OpenedMenuItemViewHolder(view : View) : RecyclerView.ViewHolder(view){
        private val img = view.findViewById<ImageView>(R.id.image_inside)
        private val name = view.findViewById<TextView>(R.id.text_inside)
        private val contains = view.findViewById<TextView>(R.id.contains)
        private val weightInfo = view.findViewById<TextView>(R.id.weightInfo)

        fun bind(currentItem : Product, position: Int, myView : View) {

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.preload)
                .error(R.drawable.preload)

            Glide.with(context)
                .applyDefaultRequestOptions(requestOptions)
                .load(try{currentItem.images[0].imageUrl}catch (e:Exception){"dd"})
                .into(img)

            name.text = currentItem.name
            contains.text = currentItem.description
            weightInfo.text = (currentItem.weight*1000).toInt().toString()

            showInfo(myView.info_fragment_RL, position)

            myView.go_back.setOnClickListener {
                val intent = Intent(context, GeneralActivity::class.java)
                intent.putExtra("back_from", commonPosition)
                context.startActivity(intent, ActivityOptions.makeCustomAnimation(context, R.anim.enter_anim_left, R.anim.exit_anim_left).toBundle())
            }


            var hleb = menu.getModifiers(currentItem, hlebGroupName)
            if (hleb.isEmpty()) hleb = menu.getModifiers(currentItem, hlebPitaGroupName)
            val bez = menu.getModifiers(currentItem, bezGroupName)
            val jalapenos = menu.getModifiers(currentItem, jalapenoGroupName)
            if (bez.isEmpty()) myView.bez_button.visibility=View.GONE
            if (jalapenos.isEmpty()){
                myView.add_button.visibility=View.GONE
                myView.singled_modifier.visibility=View.GONE
            }


            myView.RL.layoutParams = (RelativeLayout.LayoutParams(0,0))

            myView.RL.setOnTouchListener { _, _ ->
                myView.info_fragment_RL.animate().alpha(0f).duration = 200
                myView.RL.layoutParams = (RelativeLayout.LayoutParams(0,0))
                true
            }

            myView.info_button.setOnClickListener {
                myView.info_fragment_RL.animate().alpha(1f).duration = 200
                myView.RL.layoutParams = (RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT))
            }

            myView.bez_button.setOnClickListener {
                myView.expandableLayout.toggle()
            }

            myView.to_basket_button.setOnClickListener {
                val orderItem = OrderItem().fromProduct(currentItem)
                hlebModifiers.forEach { if (it.amount>0) orderItem.modifiers.add(it) }
                jalapenoModifiers.forEach { if (it.amount>0) orderItem.modifiers.add(it) }
                bezModifiers.forEach { if (it.amount>0) orderItem.modifiers.add(it) }
                Log.d("AAAAAA",hlebModifiers.toString())
                order.addToOrder(orderItem)

                val toast = Toast.makeText(context, "Добавлено в корзину", Toast.LENGTH_SHORT)
                toast.view.background.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN)
                toast.view.findViewById<TextView>(android.R.id.message).setTextColor(Color.WHITE)
                toast.show()

                setBadges()

            }

            jalapenoModifiers = MutableList(jalapenos.size){ OrderItemModifier().fromProduct(jalapenos[it],jalapenoGroupName)}
            bezModifiers = MutableList(bez.size){ OrderItemModifier().fromProduct(bez[it],bezGroupName)}
            hlebModifiers = MutableList(hleb.size){ OrderItemModifier().fromProduct(hleb[it], hlebGroupName)}
            try {
                hlebModifiers[0].amount=1
            }
            catch (e:Exception){}

            Log.d("size", bezModifiers.size.toString())


            showGroupModifier(hleb, myView.group_modifier, context)
            showModifiers(bez, myView.bez_modifier, context, "BEZ")
            showModifiers(jalapenos, myView.singled_modifier, context, "DOBAVIT")



            myView.expandableLayout.collapse()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OpenedMenuItemViewHolder {
        return OpenedMenuItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.opened_item_for_view_pager, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: OpenedMenuItemViewHolder, position: Int) {
        holder.bind(items[position], position, holder.itemView)
    }

    private fun showInfo(view: RelativeLayout, position: Int){

        view.info_name.text = items[position].name
        view.info_carbohydrates.text = try {
            items[position].carbohydrateAmount!!.toInt()
        }catch (e : Exception){}.toString()
        view.info_energy.text = try {
            items[position].energyAmount!!.toInt()
        }catch (e : Exception){0}.toString()
        view.info_proteins.text =  try {
            items[position].fiberAmount!!.toInt()
        }catch (e : Exception){0}.toString()
        view.info_fats.text =  try {
            items[position].fatAmount!!.toInt()
        }catch (e : Exception){0}.toString()
    }

    private fun showGroupModifier(data : List<Product>, lavashLayout : TabLayout, context: Context){
//        for(x in data.indices){
//            val textName = TextView(context)
//            textName.text = data[x].name
//            textName.setOnClickListener {currentPosition ->
//                lavashLayout.children.forEach { it ->
//                    (it as TextView).run {
//                        setBackgroundResource(R.drawable.group_modifiers_item_background)
//                    }
//
//                }
//                (currentPosition as TextView).run {
//                    background = null
//                }
//                hlebModifiers.forEach { it.amount = 0 }
//                hlebModifiers[x].amount = 1
//            }
//
//            textName.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, (100 / data.size).toFloat())
//            lavashLayout.setPadding(10, 10, 10, 10)
//
//            textName.gravity = Gravity.CENTER
//            textName.setTextColor(context.getResources().getColor(R.color.textColor))
//            lavashLayout.addView(textName)
//            textName.setBackgroundColor(Color.GRAY)
//            textName.setTextColor(Color.LTGRAY)
//        }
//
//        try {
//            (lavashLayout.getChildAt(0) as TextView).setBackgroundResource(R.drawable.group_modifiers_item_background)
//        }
//        catch (e:Exception){}
        for(x in data.indices){
            lavashLayout.addTab(lavashLayout.newTab().setText(data[x].name))
            }

        lavashLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                hlebModifiers.forEach { it.amount = 0 }
                hlebModifiers[tab!!.position].amount = 1
            }

        })
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

    private fun createModifierView(context: Context, product: Product, currentPosition : Int, type : String) : LinearLayout{

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

        parent.setOnClickListener {
            when(type){
                "BEZ" ->{
                    if(bezModifiers[currentPosition].amount == 0){
                        bezModifiers[currentPosition].amount = 1
//                        parent.setBackgroundColor(Color.LTGRAY)
                        parent.is_checked.setImageResource(R.drawable.ic_contacts_black_24dp)
                    }
                    else{
                        bezModifiers[currentPosition].amount = 0
//                        parent.setBackgroundColor(Color.WHITE)
                        parent.is_checked.setImageDrawable(null)
                    }
                }

                "DOBAVIT" ->{
                    if(jalapenoModifiers[currentPosition].amount == 0){
                        jalapenoModifiers[currentPosition].amount = 1
//                        parent.setBackgroundColor(Color.LTGRAY)
                        parent.is_checked.setImageResource(R.drawable.ic_favorite_black_24dp)
                    }
                    else{
                        jalapenoModifiers[currentPosition].amount = 0
//                        parent.setBackgroundColor(Color.WHITE)
                        parent.is_checked.setImageDrawable(null)
                    }
                }
            }
        }

        return parent

    }
}


