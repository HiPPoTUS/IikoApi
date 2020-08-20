package com.example.iikoapi.general.basketadapter

import Product
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SmoothScroller
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.iikoapi.R
import com.example.iikoapi.general.BasketFragment
import com.example.iikoapi.general.menu_prods
import com.example.iikoapi.general.mods_prods
import com.example.iikoapi.openedmenuitem.Ghleb
import com.example.iikoapi.openedmenuitem.GhlebPita
import com.example.iikoapi.openedmenuitem.order
import com.example.iikoapi.startapp.datatype.OrderItem
import com.example.iikoapi.startapp.datatype.OrderItemModifier
import com.example.iikoapi.utils.setBadges
import kotlinx.android.synthetic.main.card_view_for_basket.view.*


val data = menu_prods.values.elementAt(0)
lateinit var currentSupplementsItems : MutableList<Product>

class BasketRecycleViewAdapter(var clear_basket : Button, var text_empty_basket : TextView, var recyclerViewForBasket : RecyclerView, var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{

    private lateinit var basketFragment: BasketFragment
    private val TYPE_ITEMS = 1
    private val TYPE_SUPPLEMENTS = 2
    private var thisAdapter = this

    private var items: MutableList<OrderItem> = ArrayList()
    private lateinit var supplementsViewHolder : SupplementsViewHolder

    fun setBasketFragment(basketFragment: BasketFragment){
        this.basketFragment = basketFragment
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == items.size) {
            TYPE_SUPPLEMENTS
        } else {
            TYPE_ITEMS
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_ITEMS) ProductViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_view_for_basket, parent, false), thisAdapter, items, clear_basket, text_empty_basket)
        else {
            currentSupplementsItems = data.toMutableList()
            supplementsViewHolder = SupplementsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.basket_supplements, parent, false), this)
            supplementsViewHolder
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ProductViewHolder -> {
                holder.run { bind(items[position], position) }
            }

            is SupplementsViewHolder ->{
                holder.run { bind() }
            }
        }

//        holder.itemView.setOnClickListener{
//
//        }

    }

    override fun getItemCount(): Int {
        return items.size + 1
    }

    fun submitList(productList: MutableList<OrderItem>){
        items = productList
    }

    inner class ProductViewHolder
    constructor(itemView: View, private var thisAdapter: BasketRecycleViewAdapter, private var thisData: MutableList<OrderItem>, var clear_basket : Button, var text_empty_basket : TextView) : RecyclerView.ViewHolder(itemView){

        private val item_image: ImageView = itemView.item_image
        private val item_name = itemView.item_name
//        private val item_description = itemView.item_description
//        private val plus_modefer_names = itemView.plus_modefer_names
//        private val minus_modifer_names = itemView.minus_modifer_names
        private val item_amount = itemView.item_amount
//        private val item_sum = itemView.item_sum
        private val plus_button= itemView.plus_button
        private val minus_button= itemView.minus_button
        private val infoButtonBasket = itemView.infoButtonBasket

        fun bind(item: OrderItem, position: Int){

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.preload)
                .error(R.drawable.preload)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(try{item.imageUrl}catch (e:Exception){"dd"})
                .into(item_image)
            item.update()
            item_name.text = item.name
            item_amount.text = item.amount.toString()
//            item_description.text = item.info
//            item_sum.text = item.sum.toString()

            infoButtonBasket.setOnClickListener {
                basketFragment.openInfo(item.name!!, "-1", "-1", "-1", "-1")
            }

            plus_button.setOnClickListener {
                item.amount += 1
                item.update()
                thisAdapter.notifyItemChanged(position, Object())
                setBadges()
            }

            minus_button.setOnClickListener {
                item.amount -= 1
                item.update()
                thisAdapter.notifyItemChanged(position, Object())

                if(item.amount == 0){

                    data.forEach {
                        if(it.name == item.name){
                            currentSupplementsItems.add(it)
                            supplementsViewHolder.getSupplementsAdapter().notifyDataSetChanged()
//                            recyclerViewForBasket.smoothScrollToPosition(order.items.size)
                            return@forEach
                        }
                    }
                    thisData.removeAt(position)
                    thisAdapter.notifyItemRemoved(position)
                    order.items.remove(item)
                    thisAdapter.notifyDataSetChanged()

//                    data.forEach { product ->
//                        if(product.name == item.name){
//                            supplementsViewHolder.getSupplementsAdapter().setCurrentData(product)
//                            supplementsViewHolder.getSupplementsAdapter().notifyDataSetChanged()
//                            this@BasketRecycleViewAdapter.notifyDataSetChanged()
//                            return@forEach
//                        }
//                    }
                }

                if(order.items.isEmpty()){
                    clear_basket.text = "Вернуться в меню"
                    text_empty_basket.alpha = 1f
                    recyclerViewForBasket.visibility = View.GONE
                }

                setBadges()
            }

        }

    }

    class SupplementsViewHolder(itemView: View, private val basketRecycleViewAdapter : BasketRecycleViewAdapter) : RecyclerView.ViewHolder(itemView) {
        private lateinit var supplementsRecyclerView : RecyclerView
        private lateinit var supplementsAdapter : SupplementsAdapter

        fun bind(){
            supplementsRecyclerView = itemView.findViewById(R.id.supplementsRecyclerView)
            supplementsAdapter = SupplementsAdapter(basketRecycleViewAdapter)
            supplementsRecyclerView.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                supplementsRecyclerView.adapter = supplementsAdapter
            }

        }


        class SupplementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

            lateinit var supplementsName : TextView

            fun bind(product: Product){
                supplementsName = itemView.findViewById(R.id.supplementsName)
                supplementsName.text = product.name
            }
        }

        fun getSupplementsAdapter(): SupplementsAdapter {
            return supplementsAdapter
        }
    }

    class SupplementsAdapter(private val basketRecycleViewAdapter : BasketRecycleViewAdapter) : RecyclerView.Adapter<SupplementsViewHolder.SupplementViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SupplementsViewHolder.SupplementViewHolder {
            return SupplementsViewHolder.SupplementViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.supplement_item, parent, false))
        }

        override fun getItemCount(): Int {
            return currentSupplementsItems.size
        }

        override fun onBindViewHolder(holder: SupplementsViewHolder.SupplementViewHolder, position: Int) {
            holder.run { bind(currentSupplementsItems.elementAt(position)) }
            val product = currentSupplementsItems.elementAt(position)

            holder.itemView.setOnClickListener {
                var hleb= emptyList<Product>()
                product.groupModifiers!!.forEach {
                    if (it.modifierID == Ghleb[1]) hleb = mods_prods[it.modifierID]!!
                    if (it.modifierID == GhlebPita[1]) hleb = mods_prods[it.modifierID]!!
                }
                val orderItem = OrderItem().fromProduct(product)
                if (hleb.isNotEmpty())
                    orderItem.modifiers.add(OrderItemModifier().fromProduct(hleb.elementAt(0)))
                order.addToOrder(orderItem)

                setBadges()

                currentSupplementsItems.removeAt(position)
                this@SupplementsAdapter.notifyDataSetChanged()
                basketRecycleViewAdapter.notifyDataSetChanged()
            }
        }

        fun setCurrentData(product: Product){
            currentSupplementsItems.add(product)
        }

    }

}