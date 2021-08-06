package com.example.iikoapi.menu

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.iikoapi.R
import com.example.iikoapi.entities.GroupProducts
import com.example.iikoapi.entities.MerchItem
import com.example.iikoapi.entities.menu.Group
import com.example.iikoapi.entities.nomenclature.Product
import com.example.iikoapi.main.MainViewModel
import com.example.iikoapi.utils.adapters.GeneralAdapter
import com.example.iikoapi.utils.OnItemClickListener
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_menu.view.*
import javax.inject.Inject

@AndroidEntryPoint
class MenuFragment : Fragment(R.layout.fragment_menu) {

    @Inject
    lateinit var viewModel: MainViewModel

    private lateinit var groups: List<Group>
    private lateinit var groupsProducts: List<GroupProducts>

    private lateinit var menuTabLayout: TabLayout
    private lateinit var merchViewPager: ViewPager2
    private lateinit var menuViewPager: ViewPager2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        groups = viewModel.getGroups()
        groupsProducts = viewModel.getGroupsProducts()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        setUpViews()
    }

    private fun initViews(view: View) {
        menuTabLayout = view.menuTabLayout
        merchViewPager = view.merchViewPager
        menuViewPager = view.menuViewPager
    }

    private fun setUpViews() {

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
                setData(groupsProducts)
                setLayoutId(R.layout.menu_recycler_view)
                setInnerListener(object : OnItemClickListener<Product> {
                    override fun onClick(view: View, item: Product, position: Int) {
                        when (view.id) {
                            R.id.menuProductItem -> {
                                viewModel.openProduct(menuTabLayout.selectedTabPosition, position)
                            }
                            R.id.menuItemBuyButton -> {
                                Toast.makeText(
                                    requireContext(),
                                    "added to basket",
                                    Toast.LENGTH_SHORT
                                ).show()
                                viewModel.addToOrder(item)
                            }
                        }
                    }
                })
            }

        TabLayoutMediator(menuTabLayout, menuViewPager) { tab, position ->
            tab.text = groups[position].name
            tab.customView
        }.attach()

        menuViewPager.offscreenPageLimit = groups.size
    }

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
        MerchItem(id = 9, url = "https://picsum.photos/200")
    )
}