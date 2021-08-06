package com.example.iikoapi.basket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.iikoapi.R
import com.example.iikoapi.databinding.FragmentBasketBinding
import com.example.iikoapi.entities.basket.BasketItemEntity
import com.example.iikoapi.main.MainViewModel
import com.example.iikoapi.utils.OnItemClickListener
import com.example.iikoapi.utils.adapters.CustomItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BasketFragment : Fragment(R.layout.fragment_basket) {

    private lateinit var binding: FragmentBasketBinding

    @Inject
    lateinit var viewModel: MainViewModel

    private val adapter = BasketAdapter().apply {
        footerLayout = R.layout.item_footer_for_basket
        setLayoutId(R.layout.item_for_basket)
        setListener(object : OnItemClickListener<BasketItemEntity> {
            override fun onClick(view: View, item: BasketItemEntity, position: Int) {
                when (view.id) {
                    R.id.addButton -> {
                        val items = order.items.map { it.copy() }
                        order.updateItem(position)
                        updateOrder(items)
                    }

                    R.id.minusButton -> {
                        val items = order.items.map { it.copy() }
                        order.updateItem(position, false)
                        if (order.items[position].count == 0) {
                            order.items.removeAt(position)
                            this@apply.notifyItemRemoved(position)
                            this@apply.notifyItemRemoved(position)
                            for (i in 0 until order.items.size) {
                                this@apply.notifyItemChanged(i)
                            }
                            binding.isEmptyOrder = order.items.size == 0
                            return
                        }
                        updateOrder(items)
                    }

                }
                this@apply.notifyItemChanged(order.items.size)
            }

        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBasketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        adapter.apply {
            order = viewModel.order
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            isEmptyOrder = viewModel.order.isEmpty
            basketRecyclerView.apply {
                if (itemDecorationCount == 0) {
                    addItemDecoration(CustomItemDecoration())
                }
            }.adapter = adapter

            action = View.OnClickListener {
                if (binding.isEmptyOrder!!)
                    viewModel.bottomNavigationView.selectedItemId = R.id.menuFragment
                else {
                    viewModel.makeOrder(true)
                }
            }
        }
    }

}