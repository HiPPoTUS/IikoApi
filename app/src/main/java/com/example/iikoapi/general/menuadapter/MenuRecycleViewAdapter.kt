package com.example.iikoapi.general.menuadapter

import Product
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.children
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.iikoapi.R
import com.example.iikoapi.general.mods_prods
import com.example.iikoapi.openedmenuitem.*
import com.example.iikoapi.startapp.datatype.OrderItem
import com.example.iikoapi.startapp.datatype.OrderItemModifier
import com.example.iikoapi.utils.setBadges
import kotlinx.android.synthetic.main.modifier_item.view.*
import kotlinx.android.synthetic.main.open_menu_item_for_recycler_view.view.*
import kotlinx.android.synthetic.main.opened_item_for_view_pager.view.*


class MenuRecycleViewAdapter(private var context: Context, var commonPos : Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{

    private val TAG: String = "AppDebug"

    private var items: List<Product> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProductViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.open_menu_item_for_recycler_view, parent, false),
            context
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ProductViewHolder -> {
                holder.run { bind(items[position]) }
            }
        }

        holder.itemView.setOnClickListener{
            val intent = Intent(context, OpenedMenuItem::class.java)
//            intent.putExtra("image", items[position].image)
//            intent.putExtra("name", items[position].name)
            intent.putExtra("position", position)
            intent.putExtra("comonPos", commonPos)
            context.startActivity(intent, ActivityOptions.makeCustomAnimation(context, R.anim.enter_anim_right, R.anim.exit_anim_right).toBundle())
//            val ft: FragmentManager = (context as FragmentActivity).supportFragmentManager
//            val newFragment = FramentOpen(context)
//            ft.beginTransaction().replace(R.id.fragment_container, newFragment).commit()
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(blogList: List<Product>){
        items = blogList
    }

    class ProductViewHolder
    constructor(itemView: View, var context: Context): RecyclerView.ViewHolder(itemView){

        val produtc_image = itemView.product_image
        val product_name = itemView.product_name
        val product_info = itemView.product_info
        val product_weight = itemView.product_weight
        val direct_button = itemView.direct_button

        fun bind(product: Product){

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.preload)
                .error(R.drawable.preload)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(try{product.images[0].imageUrl}catch (e:Exception){"dd"})
                .into(produtc_image)

            product_name.text = product.name
            product_info.text = product.description
            product_weight.text = (product.weight!!*1000).toInt().toString()
            direct_button.text = product.price.toString()

            direct_button.setOnClickListener {
                var hleb= emptyList<Product>()
                product.groupModifiers!!.forEach {
                    if (it.modifierID == Ghleb[1]) hleb = mods_prods[it.modifierID]!!
                    if (it.modifierID == GhlebPita[1]) hleb = mods_prods[it.modifierID]!!
                }
                val orderItem = OrderItem().fromProduct(product)
                if (hleb.isNotEmpty())
                    orderItem.modifiers.add(OrderItemModifier().fromProduct(hleb.elementAt(0)))
                order.addToOrder(orderItem)

                val toast = Toast.makeText(context, "Добавлено в корзину", Toast.LENGTH_SHORT)
                toast.view.background.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN)
                toast.view.findViewById<TextView>(android.R.id.message).setTextColor(Color.WHITE)
                toast.show()
                setBadges()
            }
        }

    }

}