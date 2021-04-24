package com.example.iikoapi.general

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.iikoapi.R
import com.example.iikoapi.general.contacts.ContactsFragmentList
import com.example.iikoapi.general.contacts.ContactsFragmentMap
import com.example.iikoapi.general.contacts.InfoInterface
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_maps.view.*

class ContactsFragment(private val listener: InfoInterface): Fragment(R.layout.activity_maps) {

    val l = listOf("List", "Map")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.contactsViewPager.adapter = ContactsPagerAdapter(this)
        view.contactsViewPager.isUserInputEnabled = false

        TabLayoutMediator(view.contactsTabLayout, view.contactsViewPager) { tab, position ->
            tab.text = l[position]
            tab.customView
        }.attach()
    }

    inner class ContactsPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment){

        override fun getItemCount() = 2

        override fun createFragment(position: Int) = if(position == 0){
            ContactsFragmentList()
        }
        else{
            ContactsFragmentMap(listener)
        }

    }
}