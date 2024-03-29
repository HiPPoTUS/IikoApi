package com.example.iikoapi.contacts

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.iikoapi.R
import com.example.iikoapi.entities.District
import com.example.iikoapi.main.MainViewModel
import com.example.iikoapi.utils.InfoInterface
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_contacts.view.*
import javax.inject.Inject

@AndroidEntryPoint
class ContactsFragment : Fragment(R.layout.fragment_contacts){

    private val tabNames = listOf("Список", "Карта")

    private val districts = List(20) {
        District(adr = "adress number -> $it")
    }

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.contactsViewPager.adapter = ContactsPagerAdapter(this)
        view.contactsViewPager.isUserInputEnabled = false

        TabLayoutMediator(view.contactsTabLayout, view.contactsViewPager) { tab, position ->
            tab.text = tabNames[position]
            tab.customView
        }.attach()
    }


    companion object {
        const val TAG = 2
    }

    val INDEX_TAG get() = TAG

    inner class ContactsPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment){

        override fun getItemCount() = 2

        override fun createFragment(position: Int) = if(position == 0){
            ContactsFragmentList().also {
                it.setDistricts(districts)
            }
        }
        else{
            ContactsFragmentMap(object :
                InfoInterface {

                override fun show(district: District) {
                    viewModel.showInfo(district)
                }

            })
        }

    }
}