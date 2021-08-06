package com.example.iikoapi.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.iikoapi.R
import com.example.iikoapi.entities.start.District
import com.example.iikoapi.profile.Authorisation
import com.example.iikoapi.views.setupWithNavControllerCustom
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.view.*
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    @Inject
    lateinit var viewModel: MainViewModel

    private lateinit var district: District

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel.setMainFragment(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            val args = MainFragmentArgs.fromBundle(requireArguments())
            district = args.district
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = Navigation.findNavController(requireActivity(), R.id.container)
        viewModel.bottomNavigationView = view.bottomNavigationView
        view.bottomNavigationView.setupWithNavControllerCustom(navController)
    }

    fun openProduct(groupPosition: Int, productPosition: Int){
        val action = MainFragmentDirections.actionMainFragmentToProductsFragment(groupPosition, productPosition)
        findNavController().navigate(action)
    }

    fun openAuthorisation(type: Authorisation){
        val action = MainFragmentDirections.actionMainFragmentToUserCreationFragment(type, false)
        findNavController().navigate(action)
    }

    fun openProfile(isOrder: Boolean){
        val action = MainFragmentDirections.actionMainFragmentToAuthorisationFragment(isOrder)
        findNavController().navigate(action)
    }
}