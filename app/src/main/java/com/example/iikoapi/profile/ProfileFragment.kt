package com.example.iikoapi.profile

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.iikoapi.R
import com.example.iikoapi.contacts.ContactsFragmentDirections
import com.example.iikoapi.databinding.FragmentProfileBinding
import com.example.iikoapi.databinding.FragmentUserCreationBinding
import com.example.iikoapi.entities.start.District
import com.example.iikoapi.main.MainViewModel
import com.example.iikoapi.room.entity.User
import com.example.iikoapi.utils.LoadingState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.layout_profile_info.*
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    @Inject
    lateinit var viewModel: MainViewModel

    private lateinit var binding: FragmentProfileBinding

    override fun onResume() {
        super.onResume()
        viewModel.getUsers().observe(this, Observer {users ->
            if(users.isEmpty()){
                with(binding){
                    user = null
                }
            }
            else{
                with(binding){
                    user = users.first()
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            loginButton.setOnClickListener {
                viewModel.openAuthorisation(Authorisation.Login)
            }
            registerButton.setOnClickListener {
                viewModel.openAuthorisation(Authorisation.Registration)
            }

            logOutButton.setOnClickListener {
                viewModel.logOutUser()
                user = null
            }
//            action = View.OnClickListener {
//                when (view.id) {
//                    R.id.loginButton -> {
//                        viewModel.openAuthorisation(Authorisation.Login)
//                    }
//                    R.id.registerButton -> {
//                        viewModel.openAuthorisation(Authorisation.Registration)
//                    }
//                }
//            }
        }
    }

}