package com.example.iikoapi.main

import android.app.Activity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iikoapi.dialogs.ProductInfoDialog
import com.example.iikoapi.entities.GroupProducts
import com.example.iikoapi.entities.MerchItem
import com.example.iikoapi.entities.menu.ChildModifier
import com.example.iikoapi.entities.menu.Menu
import com.example.iikoapi.entities.menu.Modifier
import com.example.iikoapi.entities.nomenclature.Product
import com.example.iikoapi.entities.start.MenuIdBody
import com.example.iikoapi.entities.start.OrganisationIdBody
import com.example.iikoapi.entities.start.Terminals
import com.example.iikoapi.start.StartFragmentDirections
import com.example.iikoapi.utils.LoadingState
import com.example.iikoapi.utils.Repository
import com.yandex.mapkit.MapKitFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    var supportFragmentManager: FragmentManager? = null

    var activity: Activity? = null

    private lateinit var menu: Menu
    private val groupsProduct = mutableListOf<GroupProducts>()

    fun getGroups() = menu.groups_products

    fun getGroupsProducts() = groupsProduct

    fun getModifiersType(id: String) = menu.groups_modifiers.find {
        it.id == id
    }

    fun geGroupsModifiers(childModifiers: List<ChildModifier>?) = childModifiers?.let {
        mutableListOf<Modifier>().also {list ->
            childModifiers.forEach {childModifier ->
                menu.modifiers.find {modifier ->
                    modifier.id == childModifier.modifierId
                }.apply {
                    this?.let {
                        list.add(this)
                    }
                }

            }
        }
    }

    private val _loadingState = MutableLiveData<LoadingState>()
    private val loadingState: LiveData<LoadingState>
        get() = _loadingState

    private val terminalsLiveData = MutableLiveData<Terminals>(null)

    fun getTerminals(): LiveData<LoadingState>{
        viewModelScope.launch {

            _loadingState.value = LoadingState.Loading
            try {
                val terminalsList = repository.getTerminals(OrganisationIdBody())
                val terminals = Terminals(terminal = terminalsList)
                StartFragmentDirections.actionStartFragmentToContactsFragment(terminals)
                terminalsLiveData.value = terminals
                _loadingState.value = LoadingState.SuccessTerminals(terminals)
            } catch (e: Exception) {
                _loadingState.value = LoadingState.Error(e.message)
            }

//            val token = repository.authentication()
//            menu.value = token
//            val organisations = repository.getOrganisations(token)
//            val organisation = organisations[0]
//            menu.value = repository.getMenu(organisation.id!!, token)
//            MainScope().launch {  }
//            val restrictions = repository.getRestrictions(token, organisation.id)

        }
        return loadingState
    }


    fun getMenuResponse(): LiveData<LoadingState> {

        viewModelScope.launch {

            try {
                _loadingState.value = LoadingState.Loading
                val terminals = repository.getTerminals(OrganisationIdBody())
                menu = repository.getMenu(MenuIdBody(terminals.firstOrNull()?.deliveryTerminalId))
                menu.groups_products.forEachIndexed { index, group ->
                    groupsProduct.add(
                        GroupProducts(
                            products = menu.products.filter { product ->
                                product.parentGroup == group.id
                            },
                            groupName = group.name,
                            groupIndex = index
                        )
                    )
                }

                _loadingState.value = LoadingState.SuccessMenu(menu)
            } catch (e: Exception) {
                _loadingState.value = LoadingState.Error(e.message)
            }

//            val token = repository.authentication()
//            menu.value = token
//            val organisations = repository.getOrganisations(token)
//            val organisation = organisations[0]
//            menu.value = repository.getMenu(organisation.id!!, token)
//            MainScope().launch {  }
//            val restrictions = repository.getRestrictions(token, organisation.id)

        }

        return loadingState
    }

    fun setUpMapKit() {
        MapKitFactory.setApiKey("32c93b24-eff5-4556-a26f-8fc93aec62cf")
        MapKitFactory.initialize(activity)
    }

    fun getOpenProducts(position: Int) = groupsProduct[position].products

    private lateinit var mainFragment: MainFragment

    fun setMainFragment(fr: MainFragment) = apply { mainFragment = fr }

    fun openProduct(groupPosition: Int, position: Int) =
        apply { mainFragment.openProduct(groupPosition, position) }

    fun showProductInfo(product: Product) {
        ProductInfoDialog(product).show(supportFragmentManager!!, ProductInfoDialog.TAG)
    }

    fun addToOrder(product: Product, hlebPosition: Int, dobavitModifiers: List<MerchItem>) {

        hlebPosition
    }

}