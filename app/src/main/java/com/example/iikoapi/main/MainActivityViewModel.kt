package com.example.iikoapi.main

import android.app.Activity
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iikoapi.menu.ProductFragment
import com.example.iikoapi.R
import com.example.iikoapi.basket.BasketFragment
import com.example.iikoapi.contacts.ContactsFragment
import com.example.iikoapi.entities.District
import com.example.iikoapi.entities.datatype.Product
import com.example.iikoapi.menu.MenuFragment
import com.example.iikoapi.profile.ProfileFragment
import com.example.iikoapi.utils.Repository
import com.yandex.mapkit.MapKitFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val menu = MutableLiveData<String>(null)
    private val _menu: LiveData<String> = menu

    private lateinit var supportFragmentManager: FragmentManager
    private var activeFragment: Fragment = menuFragment
    private lateinit var mainContainer: FragmentContainerView

    private lateinit var activity: Activity

    fun setActivity(activity: Activity){
        this.activity = activity
    }

    var menuPosition = 0

    fun setUpMainNavigation(supportFragmentManager: FragmentManager, mainContainer: FragmentContainerView){
        this.supportFragmentManager = supportFragmentManager
        this.mainContainer = mainContainer
    }

    fun getMenu(): LiveData<String>{

        viewModelScope.launch {
            val token = repository.authentication()
            menu.value = token
//            val organisations = repository.getOrganisations(token)
//            val organisation = organisations[0]
//            menu.value = repository.getMenu(organisation.id!!, token)
//            MainScope().launch {  }
//            val restrictions = repository.getRestrictions(token, organisation.id)

        }

        return _menu
    }

    fun setUpMapKit(){
        MapKitFactory.setApiKey("32c93b24-eff5-4556-a26f-8fc93aec62cf")
        MapKitFactory.initialize(activity)
    }

    fun setUpNavigationFragments(@IdRes container: Int){

        supportFragmentManager.beginTransaction().apply {
            add(container, contactsFragment, "2").hide(contactsFragment)
            add(container, profileFragment, "3").hide(profileFragment)
            add(container, basketFragment, "4").hide(basketFragment)
            add(container, menuFragment, "1")

        }.commit()

    }

    fun changeFragment(@IdRes menuItem: Int): Boolean = when (menuItem) {
        R.id.menuItem-> {
            if(activeFragment != menuFragment){
                beginTransaction(menuFragment, menuFragment.INDEX_TAG)
                true
            }
            else{
                false
            }
        }
        R.id.contactsItem -> {
            if(activeFragment != contactsFragment){
                beginTransaction(contactsFragment, contactsFragment.INDEX_TAG)
                true
            }
            else{
                false
            }
        }
        R.id.profileItem -> {
            if(activeFragment != profileFragment){
                beginTransaction(profileFragment, profileFragment.INDEX_TAG)
                true
            }
            else{
                false
            }
        }
        R.id.basketItem -> {
            if(activeFragment != basketFragment){
                beginTransaction(basketFragment, basketFragment.INDEX_TAG)
                true
            }
            else{
                false
            }
        }
        else -> false
    }

    private fun beginTransaction(fr: Fragment, tag: Int){
        if(activeFragment.tag!!.toInt() > tag){
            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.enter_anim_left, R.anim.exit_anim_left)
                .hide(activeFragment)
                .show(fr)
                .commit()
        }
        else{
            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.enter_anim_right, R.anim.exit_anim_right)
                .hide(activeFragment)
                .show(fr)
                .commit()
        }

        activeFragment = fr
    }

    fun openProduct(products: List<Product>, position: Int){
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.enter_anim_right, R.anim.exit_anim_right)
            .show(productFragment.also { it.setProducts(products) })
            .hide(mainFragment)
            .commit()
    }


    fun openMainFragment(){
        supportFragmentManager.beginTransaction().add(mainContainer.id, productFragment).hide(productFragment).commit()
        supportFragmentManager.beginTransaction().add(mainContainer.id, mainFragment).commit()
    }

    fun showInfo(district: District){

    }

    fun back(){
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.enter_anim_left, R.anim.exit_anim_left)
            .hide(productFragment)
            .show(mainFragment)
            .commit()
    }

    companion object Fragments{
        private val menuFragment = MenuFragment()
        private val contactsFragment = ContactsFragment()
        private val profileFragment = ProfileFragment()
        private val basketFragment = BasketFragment()

        private val productFragment = ProductFragment()
        private val mainFragment = MainFragment()
    }
}