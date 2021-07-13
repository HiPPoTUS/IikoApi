package com.example.iikoapi.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.iikoapi.R
import com.example.iikoapi.databinding.FragmentContactsBinding
import com.example.iikoapi.databinding.FragmentContactsListBinding
import com.example.iikoapi.entities.start.District
import com.example.iikoapi.entities.start.Terminals
import com.example.iikoapi.main.MainFragmentArgs
import com.example.iikoapi.main.MainViewModel
import com.example.iikoapi.utils.InfoInterface
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_contacts.view.*
import javax.inject.Inject

@AndroidEntryPoint
class ContactsFragment : Fragment(R.layout.fragment_contacts){

    private lateinit var binding: FragmentContactsBinding

    private val districts = List(20) {
        District(adr = "adress number -> $it")
    }

    private lateinit var terminals: Terminals

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        arguments?.let {
            val args = ContactsFragmentArgs.fromBundle(requireArguments())
            terminals = args.terminals
        }
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.contactsViewPager.adapter = ContactsPagerAdapter(this)
        view.contactsViewPager.isUserInputEnabled = false
        val t = view.downloading

        val tabNames = listOf(
            requireContext().resources.getString(R.string.contacts_fragment_list),
            requireContext().resources.getString(R.string.contacts_fragment_map)
        )

        TabLayoutMediator(view.contactsTabLayout, view.contactsViewPager) { tab, position ->
            tab.text = tabNames[position]
            tab.customView
        }.attach()

    }

    inner class ContactsPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment){

        override fun getItemCount() = 2

        override fun createFragment(position: Int) = if(position == 0){
            ContactsFragmentList(binding.downloading).also {
                it.setDistricts(districts)
            }
        }
        else{
            ContactsFragmentMap(object :
                InfoInterface {

                override fun show(district: District) {
//                    viewModel.showInfo(district)
                }

            })
        }

    }
}