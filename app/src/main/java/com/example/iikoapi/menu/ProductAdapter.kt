package com.example.iikoapi.menu

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.iikoapi.entities.datatype.Product
import com.example.iikoapi.main.MainViewModel


class ProductAdapter(fragment: Fragment, private val products: List<Product>, private val viewModel: MainViewModel): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = products.size

    override fun createFragment(position: Int): Fragment = OpenProductFragment(products[position], viewModel)
}




//class ProductAdapter<T>(): GeneralAdapter<T>(){
//
//    private lateinit var adapter: ModifiersAdapter<MerchItem>
//
//    private val items = listOf(
//        MerchItem(id = 0, url = "https://picsum.photos/200"),
//        MerchItem(id = 1, url = "https://picsum.photos/200"),
//        MerchItem(id = 2, url = "https://picsum.photos/200"),
//        MerchItem(id = 3, url = "https://picsum.photos/200"),
//        MerchItem(id = 4, url = "https://picsum.photos/200"),
//        MerchItem(id = 5, url = "https://picsum.photos/200"),
//        MerchItem(id = 6, url = "https://picsum.photos/200"),
//        MerchItem(id = 7, url = "https://picsum.photos/200"),
//        MerchItem(id = 8, url = "https://picsum.photos/200"),
//        MerchItem(id = 9, url = "https://picsum.photos/200"),
//    )
//
//    fun setDobavitAdapter(adapter: ModifiersAdapter<MerchItem>){
//        this.adapter = adapter
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//
//        val layoutInflater = LayoutInflater.from(parent.context)
//        try {
//            binding = LayoutForProductBinding.inflate(layoutInflater, parent, false)
//        } catch (e: Exception){}
//
//
//        return ViewHolder(parent.context, binding)
//
//    }
//
//    var binding: LayoutForProductBinding? = null
//
//    override fun getArguments(position: Int) = super.getArguments(position).apply {
//        put(BR.dobavitList, items)
//        put(BR.tabLayout, binding?.hlebModifierTabLayout)
//        put(BR.hlebPriceTabLayout, binding?.hlebPriceTabLayout)
//        put(BR.hlebPriceExpandableLayout, binding?.hlebPriceExpandableLayout)
//        put(BR.dobavitAdapter, adapter)
//        put(BR.deleteExpandableLayout, binding?.deleteExpandableLayout)
//    }
//}