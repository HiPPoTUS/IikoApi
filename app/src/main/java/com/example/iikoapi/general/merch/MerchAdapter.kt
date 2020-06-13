package com.example.iikoapi.general.merch

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.iikoapi.R
import com.example.iikoapi.general.menuadapter.MenuRecycleViewAdapter
import com.example.iikoapi.startapp.datatype.Product

class MerchAdapter(private var items : List<Product>) : RecyclerView.Adapter<MerchAdapter.MerchViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MerchViewHolder {
        return MerchViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_for_merch_item,
                parent,
                false
            )
        )
    }


    inner class MerchViewHolder(view : View) : RecyclerView.ViewHolder(view){
        private val rView = view.findViewById<ImageView>(R.id.merch_image)

        fun bind(product: Product, position: Int) {
            rView.apply {
                val requestOptions = RequestOptions()
                    .placeholder(R.drawable.preload)
                    .error(R.drawable.preload)

                Glide.with(itemView.context)
                    .applyDefaultRequestOptions(requestOptions)
                    .load(try{product.images[0].imageUrl}catch (e:Exception){"dd"})
                    .into(this)
            }

        }
    }


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MerchViewHolder, position: Int) {
        holder.bind(items[position], position)

        holder.itemView.setOnClickListener {
            Log.d("tag", "tap $position")
        }

    }
}