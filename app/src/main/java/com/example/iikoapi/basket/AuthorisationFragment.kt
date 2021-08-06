package com.example.iikoapi.basket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.iikoapi.R
import com.example.iikoapi.databinding.FragmentProfileBinding
import com.example.iikoapi.main.MainFragmentDirections
import com.example.iikoapi.main.MainViewModel
import com.example.iikoapi.profile.Authorisation
import com.example.iikoapi.profile.UserCreationFragmentArgs
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class AuthorisationFragment : Fragment(R.layout.fragment_profile) {

    @Inject
    lateinit var viewModel: MainViewModel

    private lateinit var binding: FragmentProfileBinding

    var isOrder by Delegates.notNull<Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        arguments?.let {
            val args = AuthorisationFragmentArgs.fromBundle(requireArguments())
            isOrder = args.isOrder
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            user = null

            loginButton.setOnClickListener {
                val action = AuthorisationFragmentDirections.actionAuthorisationFragmentToUserCreationFragment(Authorisation.Login, isOrder)
                findNavController().navigate(action)
            }
            registerButton.setOnClickListener {
                val action = AuthorisationFragmentDirections.actionAuthorisationFragmentToUserCreationFragment(Authorisation.Registration, isOrder)
                findNavController().navigate(action)
            }

        }
    }

}