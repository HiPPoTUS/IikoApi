//package com.example.iikoapi.old.entities.old.general.merch
//
//import android.app.ActivityOptions
//import android.content.Context
//import android.content.Intent
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.bumptech.glide.request.RequestOptions
//import com.example.iikoapi.R
//import com.example.iikoapi.entities.datatype.Product
//import com.example.iikoapi.old.entities.old.openedmenuitem.OpenedMenuItem
//
//class MerchAdapter() : RecyclerView.Adapter<MerchAdapter.MerchViewHolder>() {
//
//    private var products = listOf<Product>()
//
//    fun setData(products: List<Product>){
//        this.products = products
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MerchViewHolder {
//        return MerchViewHolder(
//            LayoutInflater.from(parent.context).inflate(
//                R.layout.layout_for_merch_item,
//                parent,
//                false
//            )
//        )
//    }
//
//
//    inner class MerchViewHolder(view : View) : RecyclerView.ViewHolder(view){
//        private val rView = view.findViewById<ImageView>(R.id.merch_image)
//
//        fun bind(product: Product, position: Int) {
//            rView.apply {
//                val requestOptions = RequestOptions()
//                    .placeholder(R.drawable.shav_placeholder)
//                    .error(R.drawable.preload)
//
//                Glide.with(itemView.context)
//                    .applyDefaultRequestOptions(requestOptions)
//                    .load(try{product.images[0].imageUrl}catch (e:Exception){"dd"})
//                    .into(this)
//            }
//
//        }
//    }
//
//
//    override fun getItemCount() = products.size
//
//    override fun onBindViewHolder(holder: MerchViewHolder, position: Int) {
//        holder.bind(items[position], position)
//
//        holder.itemView.setOnClickListener {
//            Log.d("tag", "tap $position")
//
//            val intent = Intent(context, OpenedMenuItem::class.java)
////            intent.putExtra("image", items[position].image)
////            intent.putExtra("name", items[position].name)
//            intent.putExtra("position", position)
//            intent.putExtra("comonPos", 0)
//            context.startActivity(intent, ActivityOptions.makeCustomAnimation(context, R.anim.enter_anim_right, R.anim.exit_anim_right).toBundle())
//
//
//        }
//
//    }
//}