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
import kotlinx.android.synthetic.main.opened_item_for_view_pager.view.*


var order = Order()
var hlebGroupName = listOf<String?>("Тест не трогать !","550a4774-3a0f-4d8c-a0f7-bc176fd5a489")
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

        fun bind(data : Product) {

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.preload)
                .error(R.drawable.preload)

            Glide.with(context)
                .applyDefaultRequestOptions(requestOptions)
                .load(try{data.images[0].imageUrl}catch (e:Exception){"dd"})
                .into(img)

            name.text = data.name
            contains.text = data.description
            weightInfo.text = (data.weight*1000).toInt().toString()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OpenedMenuItemViewHolder {
        return OpenedMenuItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.opened_item_for_view_pager, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: OpenedMenuItemViewHolder, position: Int) {
        val currentItem = items[position]
        holder.bind(currentItem)
        val myView = holder.itemView

        showInfo(myView.info_fragment_RL, position)

        myView.go_back.setOnClickListener {
            val intent = Intent(context, GeneralActivity::class.java)
            intent.putExtra("back_from", commonPosition)
            context.startActivity(intent, ActivityOptions.makeCustomAnimation(context, R.anim.enter_anim_left, R.anim.exit_anim_left).toBundle())
        }

        val hleb = menu.getModifiers(currentItem, hlebGroupName)
        val bez = menu.getModifiers(currentItem, bezGroupName)
        val jalapenos = menu.getModifiers(currentItem, jalapenoGroupName)
        Log.d("modf", currentItem.groupModifiers.toString())
        Log.d("modf", hleb.toString())
        Log.d("modf", bez.toString())
        Log.d("modf", jalapenos.toString())

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
//            myView.bez_grid_view.layoutParams = (RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT))
//            myView.bez_fragment_RL.animate().alpha(1f).duration = 200
//            myView.RL.layoutParams = (RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT))
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
        showBezModifier(bez, myView.bez_modifier, context)
        showSingledModifier(jalapenos, myView.singled_modifier, context)


        myView.expandableLayout.collapse()

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

    private fun showGroupModifier(data : List<Product>, lavashLayout : LinearLayout, context: Context){
        for(x in data.indices){
            val textName = TextView(context)
            textName.text = data[x].name
            textName.setOnClickListener {currentPosition ->
                lavashLayout.children.forEach { it ->
                    (it as TextView).run {
                        setBackgroundColor(Color.GRAY)
                        setTextColor(Color.LTGRAY)
                    }

                }
                (currentPosition as TextView).run {
                    textName.setBackgroundColor(Color.LTGRAY)
                    textName.setTextColor(Color.GRAY)
                }
                hlebModifiers.forEach { it.amount = 0 }
                hlebModifiers[x].amount = 1
            }
            textName.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, (100 / data.size).toFloat())
            lavashLayout.setPadding(10, 10, 10, 10)
//            lavashLayout.setBackgroundColor(Color.RED)
            textName.gravity = Gravity.CENTER
            lavashLayout.addView(textName)
            textName.setBackgroundColor(Color.GRAY)
            textName.setTextColor(Color.LTGRAY)
        }

        try {
            (lavashLayout.getChildAt(0) as TextView).setBackgroundColor(Color.LTGRAY)
            (lavashLayout.getChildAt(0)as TextView).setTextColor(Color.BLACK)
        }
        catch (e:Exception){}

    }

    private fun showSingledModifier(data : List<Product>, tableLayout : TableLayout, context: Context){

        val COLUMNS = 3
        val ROWS = data.size / COLUMNS
        val EXTRA_ITEMS = data.size - ROWS * COLUMNS


        for (i in 0 until ROWS) {
            val tableRow = TableRow(context)
            tableRow.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)
            for (j in 0 until COLUMNS) {

                tableRow.addView(createViewForTableRow(context, data[i * 3 + j], i * 3 + j))
            }
            tableLayout.addView(tableRow, i)
        }

        val tableRow = TableRow(context)
        tableRow.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)
        for(i in 0 until EXTRA_ITEMS){
            tableRow.addView(createViewForTableRow(context, data[ROWS * COLUMNS + i], ROWS * COLUMNS + i))
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

    private fun createViewForTableRow(context: Context, product: Product, currentPosition : Int) : LinearLayout{
        val linearLayout = LinearLayout(context)
        linearLayout.orientation = LinearLayout.VERTICAL
        val param = TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        linearLayout.layoutParams = param

        val img = ImageView(context)
        val requestOptions = RequestOptions().placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background)
        Glide.with(img.context).applyDefaultRequestOptions(requestOptions).load(try{product.images[0].imageUrl}catch (e:Exception){"dd"}).into(img)
        val par = LinearLayout.LayoutParams(200, 200);
        par.gravity = Gravity.CENTER
        img.layoutParams = par
        linearLayout.addView(img)

        val name = TextView(context)
        name.text = product.name
        name.gravity = Gravity.CENTER

        linearLayout.addView(name)

        val price = TextView(context)
        price.text = product.price.toString()
        price.gravity = Gravity.CENTER

        linearLayout.addView(price)


        linearLayout.setOnClickListener {
            if(jalapenoModifiers[currentPosition].amount == 0){
                jalapenoModifiers[currentPosition].amount = 1
                linearLayout.setBackgroundColor(Color.LTGRAY)
            }
            else{
                jalapenoModifiers[currentPosition].amount = 0
                linearLayout.setBackgroundColor(Color.WHITE)
            }
        }
        return linearLayout
    }

    private fun showBezModifier(data : List<Product>, tableLayout : TableLayout, context: Context){

        val COLUMNS = 3
        val ROWS = data.size / COLUMNS
        val EXTRA_ITEMS = data.size - ROWS * COLUMNS


        for (i in 0 until ROWS) {
            val tableRow = TableRow(context)
            tableRow.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)
            for (j in 0 until COLUMNS) {

                tableRow.addView(createViewForTableRowBez(context, data[i * 3 + j], i * 3 + j))
            }
            tableLayout.addView(tableRow, i)
        }

        val tableRow = TableRow(context)
        tableRow.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)
        for(i in 0 until EXTRA_ITEMS){
            tableRow.addView(createViewForTableRowBez(context, data[ROWS * COLUMNS + i], ROWS * COLUMNS + i))
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

    private fun createViewForTableRowBez(context: Context, product: Product, currentPosition : Int) : LinearLayout{
        val linearLayout = LinearLayout(context)
        linearLayout.orientation = LinearLayout.VERTICAL
        val param = TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        linearLayout.layoutParams = param

        val img = ImageView(context)
        val requestOptions = RequestOptions().placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background)
        Glide.with(img.context).applyDefaultRequestOptions(requestOptions).load(try{product.images[0].imageUrl}catch (e:Exception){"dd"}).into(img)
        val par = LinearLayout.LayoutParams(200, 200);
        par.gravity = Gravity.CENTER
        img.layoutParams = par
        linearLayout.addView(img)

        val name = TextView(context)
        name.text = product.name
        name.gravity = Gravity.CENTER

        linearLayout.addView(name)

        val price = TextView(context)
        price.text = product.price.toString()
        price.gravity = Gravity.CENTER

        linearLayout.addView(price)


        linearLayout.setOnClickListener {
            if(bezModifiers[currentPosition].amount == 0){
                bezModifiers[currentPosition].amount = 1
                linearLayout.setBackgroundColor(Color.LTGRAY)
            }
            else{
                bezModifiers[currentPosition].amount = 0
                linearLayout.setBackgroundColor(Color.WHITE)
            }
        }
        return linearLayout
    }
}


