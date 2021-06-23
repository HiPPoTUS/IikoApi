package com.example.iikoapi.menu

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.iikoapi.R
import com.example.iikoapi.entities.GroupProducts
import com.example.iikoapi.entities.MerchItem
import com.example.iikoapi.entities.datatype.Image
import com.example.iikoapi.entities.datatype.Product
import com.example.iikoapi.main.MainViewModel
import com.example.iikoapi.utils.GeneralAdapter
import com.example.iikoapi.utils.OnItemClickListener
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.yandex.mapkit.map.CameraPosition
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_menu.view.*
import javax.inject.Inject

@AndroidEntryPoint
class MenuFragment : Fragment(R.layout.fragment_menu){

    @Inject
    lateinit var viewModel: MainViewModel

    private lateinit var menuTabLayout: TabLayout
    private lateinit var merchViewPager: ViewPager2
    private lateinit var menuViewPager: ViewPager2


    private val items = listOf(
        MerchItem(id = 0, url = "https://picsum.photos/200"),
        MerchItem(id = 1, url = "https://picsum.photos/200"),
        MerchItem(id = 2, url = "https://picsum.photos/200"),
        MerchItem(id = 3, url = "https://picsum.photos/200"),
        MerchItem(id = 4, url = "https://picsum.photos/200"),
        MerchItem(id = 5, url = "https://picsum.photos/200"),
        MerchItem(id = 6, url = "https://picsum.photos/200"),
        MerchItem(id = 7, url = "https://picsum.photos/200"),
        MerchItem(id = 8, url = "https://picsum.photos/200"),
        MerchItem(id = 9, url = "https://picsum.photos/200"),
    )

    private val pr = listOf(
        Product(name = "first 0", description = "1 descr", images = listOf(Image(imageUrl = "https://picsum.photos/300")), weight = 1.0, price = 1, carbohydrateAmount = 1.0, fatAmount = 1.0, fiberAmount = 1.0, energyAmount = 1.0),
        Product(name = "first 1", description = "2 descr", images = listOf(Image(imageUrl = "https://picsum.photos/300")), weight = 2.0, price = 2, carbohydrateAmount = 2.0, fatAmount = null, fiberAmount = 1.0, energyAmount = 1.0),
        Product(name = "first 2", description = "3 descr", images = listOf(Image(imageUrl = "https://picsum.photos/300")), weight = 3.0, price = 3, carbohydrateAmount = 3.0, fatAmount = 1.0, fiberAmount = null, energyAmount = 1.0),
        Product(name = "first 3", description = "4 descr", images = listOf(Image(imageUrl = "https://picsum.photos/300")), weight = 4.0, price = 4, carbohydrateAmount = 4.0, fatAmount = 1.0, fiberAmount = 1.0, energyAmount = 1.0),
        Product(name = "first 4", description = "5 descr", images = listOf(Image(imageUrl = "https://picsum.photos/300")), weight = 5.0, price = 5, carbohydrateAmount = 5.0, fatAmount = 1.0, fiberAmount = 1.0, energyAmount = 1.0),
        Product(name = "first 5", description = "6 descr", images = listOf(Image(imageUrl = "https://picsum.photos/300")), weight = 6.0, price = 6, carbohydrateAmount = 6.0, fatAmount = 1.0, fiberAmount = 1.0, energyAmount = 1.0),
        Product(name = "first 6", description = "7 descr", images = listOf(Image(imageUrl = "https://picsum.photos/300")), weight = 7.0, price = 7, carbohydrateAmount = 7.0, fatAmount = 1.0, fiberAmount = 1.0, energyAmount = 1.0),
    )

    private val groupProducts = listOf(
        GroupProducts(group = 0, groupName = "first", products = pr),
        GroupProducts(group = 1, groupName = "second", products = pr),
        GroupProducts(group = 2, groupName = "third", products = pr),
        GroupProducts(group = 3, groupName = "fourth", products = pr),
        GroupProducts(group = 4, groupName = "fifth", products = pr),
    )

    private val groups = groupProducts.map {
        it.groupName
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        setUpViews()
    }

    private fun initViews(view: View){
        menuTabLayout = view.menuTabLayout
        merchViewPager = view.merchViewPager
        menuViewPager = view.menuViewPager
    }

    private fun setUpViews(){

        merchViewPager.adapter = GeneralAdapter<MerchItem>()
            .apply {
                setData(items)
                setLayoutId(R.layout.layout_for_merch_item)
                setListener(object : OnItemClickListener<MerchItem> {
                    override fun onClick(view: View, item: MerchItem, position: Int) {
                        when (view.id) {
                            R.id.merchItem -> Log.d("click", "merchItem")
                        }
                    }
                })
            }


        menuViewPager.adapter = MenuAdapter<GroupProducts>()
            .apply {
                setData(groupProducts)
                setLayoutId(R.layout.menu_recycler_view)
                setInnerListener(object : OnItemClickListener<Product>{
                    override fun onClick(view: View, item: Product, position: Int) {
                        when (view.id) {
                            R.id.menuProductItem -> viewModel.openProduct(groupProducts[menuTabLayout.selectedTabPosition].products, position)
                            R.id.menuItemBuyButton -> Log.d("click", "byeButton")
                        }
                    }
                })
            }

        TabLayoutMediator(menuTabLayout, menuViewPager) { tab, position ->
            tab.text = groups[position]
            tab.customView
        }.attach()

        menuViewPager.offscreenPageLimit = groupProducts.size
//        view.menuViewPager.setCurrentItem(viewModel.menuPosition, false)
    }


    companion object {
        const val TAG = 1
    }

    val INDEX_TAG get() = TAG
}