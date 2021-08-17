package com.example.iikoapi.menu.openedProduct

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.view.marginBottom
import androidx.fragment.app.Fragment
import com.example.iikoapi.R
import com.example.iikoapi.databinding.LayoutForProductBinding
import com.example.iikoapi.entities.ChildRemove
import com.example.iikoapi.entities.MerchItem
import com.example.iikoapi.entities.ParentRemove
import com.example.iikoapi.entities.menu.ModifiersTypes
import com.example.iikoapi.entities.nomenclature.Product
import com.example.iikoapi.main.MainViewModel
import com.example.iikoapi.utils.DimensionsConverter.dp
import com.example.iikoapi.utils.ModifiersItemDecoration
import com.example.iikoapi.utils.OnItemClickListener
import com.example.iikoapi.utils.adapters.ExpandableScrollableAdapter

class OpenProductFragment(private val product: Product, private val viewModel: MainViewModel) :
    Fragment() {

    private lateinit var binding: LayoutForProductBinding
    private val dobavitAdapter = ModifiersAdapter<MerchItem>().apply {
        setLayoutId(R.layout.item_dobavit)

    }

    private val removeAdapter = ExpandableScrollableAdapter<ChildRemove>().apply {
        setChildLayoutId(R.layout.item_remove)
        setParentLayoutId(R.layout.item_header_remove)
    }

    private val removeModifiers = mutableListOf<ChildRemove>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        product.groupModifiers?.forEach { groupModifierProduct ->
            when (viewModel.getModifiersType(groupModifierProduct.modifierId)?.getType()) {
                ModifiersTypes.BEZ -> {
                    viewModel.geGroupsModifiers(groupModifierProduct.childModifiers)!!
                        .map { modifier ->
                            ChildRemove(item = modifier)
                        }.also {
                            removeAdapter.items = mutableListOf(ParentRemove(
                                parent = requireContext().resources.getString(R.string.product_for_layout_remove_ingredients),
                                children = it
                            ))
                            removeAdapter.context = requireContext()
                            removeAdapter.listener = object : OnItemClickListener<ChildRemove>{
                                override fun onClick(view: View, item: ChildRemove, position: Int) {
                                    if(removeModifiers.contains(item)) removeModifiers.remove(item)
                                    else removeModifiers.add(item)
                                }

                            }
                        }
                }
                ModifiersTypes.HLEB_WITH_PITA -> {
                    product.setHleb(viewModel.geGroupsModifiers(groupModifierProduct.childModifiers))
                }
                ModifiersTypes.HLEB_WITHOUT_PITA -> {
                    product.setHleb(viewModel.geGroupsModifiers(groupModifierProduct.childModifiers))
                }
                ModifiersTypes.DOBAVIT -> TODO()
                null -> TODO()
            }
        }

    }

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
            if(removeAdapter.items.size == 0){
                removeRecyclerView.isVisible = false
            }

            if(dobavitAdapter.getData().isEmpty()){
                recyclerViewDobavit.isVisible = false
                addLayout.isVisible = false
            }

            item = product
            action = View.OnClickListener { view ->
                when (view.id) {
                    addToBasketButton.id -> {

                        viewModel.addToOrder(
                            product,
                            binding.hlebModifierTabLayout.selectedTabPosition,
                            dobavitAdapter.getModifiers(),
                            if(removeModifiers.isEmpty()) null else removeModifiers.map { it.item }
                        )

                        Toast.makeText(requireContext(), requireContext().resources.getString(R.string.product_for_layout_added_to_basket), Toast.LENGTH_SHORT)
                            .show()
                    }
                    backButton.id -> activity?.onBackPressed()
                    infoButton.id -> viewModel.showProductInfo(product)

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
            dobavitAdapter.apply {
                setData(items)

            }
            binding.recyclerViewDobavit.adapter = dobavitAdapter
        }

        binding.removeRecyclerView.adapter = removeAdapter.apply {
            this.nestedScrollView = binding.nestedScrollView
            this.removeRecyclerView = binding.removeRecyclerView
            binding.nestedScrollView.post {
                this.space = getSpace()
            }
        }

    }

    private fun getSpace(): Int {
        val tv = TypedValue()
        val space = binding.addToBasketButton.height +
                binding.addToBasketButton.marginBottom +
                binding.constraitLt.paddingBottom +
                50 * requireActivity().resources.displayMetrics.density.toInt() +
                if (requireActivity().theme.resolveAttribute(
                        android.R.attr.actionBarSize, tv,
                        true
                    )
                ) {
                    TypedValue.complexToDimensionPixelSize(
                        tv.data,
                        requireActivity().resources.displayMetrics
                    )
                } else {
                    0
                } -
                requireActivity().windowManager.defaultDisplay.height

        return space
    }

    private val items = emptyList<MerchItem>()
}