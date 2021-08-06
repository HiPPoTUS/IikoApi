package com.example.iikoapi.profile

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.iikoapi.R
import com.example.iikoapi.databinding.FragmentUserCreationBinding
import com.example.iikoapi.main.MainViewModel
import com.example.iikoapi.room.entity.User
import com.example.iikoapi.utils.LoadingState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class UserCreationFragment : Fragment(R.layout.fragment_user_creation) {

    private lateinit var binding: FragmentUserCreationBinding

    @Inject
    lateinit var viewModel: MainViewModel

    lateinit var type: Authorisation
    private var isOrder by Delegates.notNull<Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserCreationBinding.inflate(inflater, container, false)

        arguments?.let {
            val args = UserCreationFragmentArgs.fromBundle(requireArguments())
            type = args.type
            isOrder = args.isOrder
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            type = this@UserCreationFragment.type
            action = View.OnClickListener {

                when(type){
                    is Authorisation.Login ->{
                        viewModel.loginUser(User(
                            name = nameEditText.text.toString(),
                            phone = phoneEditText.text.toString(),
                            password = passwordEditText.text.toString()
                        )).observe(viewLifecycleOwner, Observer {loadingState ->
                            loadingStateResponse(loadingState)
                        })
                    }
                    Authorisation.Registration -> {
                        viewModel.registerUser(User(
                            name = nameEditText.text.toString(),
                            phone = phoneEditText.text.toString(),
                            password = passwordEditText.text.toString()
                        )).observe(viewLifecycleOwner, Observer {loadingState ->
                            loadingStateResponse(loadingState)
                        })
                    }
                }


            }
        }
    }

    private fun loadingStateResponse(loadingState: LoadingState){
        when(loadingState){
            is LoadingState.Loading -> {

            }
            is LoadingState.SuccessRegistration -> {
                if(isOrder){
                    findNavController().navigate(R.id.action_userCreationFragment_to_orderFragment)
                }
                else{
                    requireActivity().onBackPressed()
                }
            }
            is LoadingState.SuccessLogin -> {
                if(isOrder){
                    findNavController().navigate(R.id.action_userCreationFragment_to_orderFragment)
                }
                else{
                    requireActivity().onBackPressed()
                }
            }
            else -> {
                showError(loadingState)
            }
        }
    }

    private fun showError(loadingState: LoadingState){
        val error = (loadingState as LoadingState.Error).error
        AlertDialog.Builder(context)
            .setTitle(requireContext().resources.getString(R.string.start_fragment_error_title))
            .setMessage(requireContext().resources.getString(R.string.start_fragment_error_subtitle))
            .setPositiveButton(android.R.string.yes){ dialog, _ ->
                dialog.dismiss()
            }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

}