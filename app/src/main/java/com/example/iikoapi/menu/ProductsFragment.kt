package com.example.iikoapi.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.iikoapi.R
import com.example.iikoapi.entities.nomenclature.Product
import com.example.iikoapi.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.products_view_pager.view.*
import javax.inject.Inject

@AndroidEntryPoint
class ProductsFragment : Fragment(R.layout.products_view_pager) {

    @Inject
    lateinit var viewModel: MainViewModel

    private lateinit var args: ProductsFragmentArgs

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        arguments?.let {
            args = ProductsFragmentArgs.fromBundle(requireArguments())
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.productsViewPager.adapter = ProductAdapter(this, viewModel.getOpenProducts(args.groupPosition), viewModel)
        view.productsViewPager.setCurrentItem(args.productPosition, false)

    }


}
