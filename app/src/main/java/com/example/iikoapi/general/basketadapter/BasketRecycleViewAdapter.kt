package com.example.iikoapi.general.basketadapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.iikoapi.R
import com.example.iikoapi.openedmenuitem.order
import com.example.iikoapi.startapp.datatype.OrderItem
import com.example.iikoapi.utils.setBadges
import kotlinx.android.synthetic.main.card_view_for_basket.view.*

class BasketRecycleViewAdapter(var clear_basket : Button, var text_empty_basket : TextView) : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    private var thisAdapter = this

    private var items: MutableList<OrderItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProductViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_view_for_basket,
                parent,
                false
            )
        , thisAdapter, items,
        clear_basket, text_empty_basket)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ProductViewHolder -> {
                holder.run { bind(items[position], position) }
            }
        }

        holder.itemView.setOnClickListener{
//            var intent = Intent(context, OpenedMenuItem::class.java)
////            intent.putExtra("image", items[position].image)
////            intent.putExtra("name", items[position].name)
//            intent.putExtra("position", position)
//            intent.putExtra("comonPos", commonPos)
//            context.startActivity(intent, ActivityOptions.makeCustomAnimation(context, R.anim.enter_anim_right, R.anim.exit_anim_right).toBundle())
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(productList: MutableList<OrderItem>){
        items = productList
    }

    class ProductViewHolder
    constructor(itemView: View, private var thisAdapter: BasketRecycleViewAdapter, private var thisData: MutableList<OrderItem>, var clear_basket : Button, var text_empty_basket : TextView): RecyclerView.ViewHolder(itemView){

        private val item_image: ImageView = itemView.item_image
        private val item_name = itemView.item_name
        private val item_description = itemView.item_description
//        private val plus_modefer_names = itemView.plus_modefer_names
//        private val minus_modifer_names = itemView.minus_modifer_names
        private val item_amount = itemView.item_amount
        private val item_sum = itemView.item_sum
        private val plus_button= itemView.plus_button
        private val minus_button= itemView.minus_button

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
            item_description.text = item.info
            item_sum.text = item.sum.toString()

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

                    thisData.removeAt(position)
                    thisAdapter.notifyItemRemoved(position)
                    order.items.remove(item)
                    thisAdapter.notifyDataSetChanged()
                }

                if(order.items.isEmpty()){
                    clear_basket.text = "Вернуться в меню"
                    text_empty_basket.alpha = 1f
                }

                setBadges()
            }

        }

    }

}