package com.example.iikoapi.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.fragment.app.Fragment
import com.example.iikoapi.R
import com.example.iikoapi.databinding.LayoutForProductBinding
import com.example.iikoapi.entities.MerchItem
import com.example.iikoapi.entities.datatype.Product
import com.example.iikoapi.main.MainViewModel
import com.example.iikoapi.utils.DimensionsConverter.dp
import com.example.iikoapi.utils.ModifiersItemDecoration
import com.example.iikoapi.utils.OnItemClickListener
import com.google.android.material.tabs.TabLayout

class OpenProductFragment(private val product: Product, private val viewModel: MainViewModel) :
    Fragment() {

    private lateinit var binding: LayoutForProductBinding
    private lateinit var dobavitAdapter: ModifiersAdapter<MerchItem>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutForProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            item = product
            action = View.OnClickListener { view ->
                when (view.id) {
                    addToBasketButton.id -> viewModel.addToOreder(
                        product,
                        binding.hlebModifierTabLayout.selectedTabPosition,
                        dobavitAdapter.getModifiers()
                    )
                    backButton.id -> viewModel.back()
                    infoButton.id -> viewModel.showProductInfo(product)
                    deleteButton.id -> {
                        deleteExpandableLayout.toggle()
                        nestedScrollView.postDelayed({ nestedScrollView.fullScroll(ScrollView.FOCUS_DOWN) }, 200)
                    }
                }
            }
        }

        setUpDobavitRecyclerView()

    }

    private fun setUpDobavitRecyclerView() {

        if (binding.recyclerViewDobavit.itemDecorationCount == 0) {
            binding.recyclerViewDobavit.addItemDecoration(ModifiersItemDecoration(8.dp, 3))
        }

        binding.recyclerViewDobavit.post {
            dobavitAdapter = ModifiersAdapter<MerchItem>().apply {
                setListener(object : OnItemClickListener<MerchItem> {
                    override fun onClick(view: View, item: MerchItem, position: Int) {
                        dobavitAdapter.changeOrder(position)
                    }

                })
                setWidth(binding.recyclerViewDobavit.width / 3 - 8.dp)
                setData(items)
                setLayoutId(R.layout.item_dobavit)
            }
            binding.recyclerViewDobavit.adapter = dobavitAdapter
        }
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
        MerchItem(id = 9, url = "https://picsum.photos/200"),
    )
}