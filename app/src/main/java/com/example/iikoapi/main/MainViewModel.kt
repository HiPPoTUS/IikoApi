package com.example.iikoapi.main

import android.R.id
import android.app.Activity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.*
import com.example.iikoapi.dialogs.ProductInfoDialog
import com.example.iikoapi.entities.GroupProducts
import com.example.iikoapi.entities.MerchItem
import com.example.iikoapi.entities.basket.BasketEntity
import com.example.iikoapi.entities.basket.BasketItem
import com.example.iikoapi.entities.menu.ChildModifier
import com.example.iikoapi.entities.menu.Menu
import com.example.iikoapi.entities.menu.Modifier
import com.example.iikoapi.entities.nomenclature.Product
import com.example.iikoapi.entities.profile.LoginRequest
import com.example.iikoapi.entities.start.MenuIdBody
import com.example.iikoapi.entities.start.OrganisationIdBody
import com.example.iikoapi.entities.start.Terminals
import com.example.iikoapi.profile.Authorisation
import com.example.iikoapi.room.entity.User
import com.example.iikoapi.start.StartFragmentDirections
import com.example.iikoapi.utils.LoadingState
import com.example.iikoapi.utils.Repository
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yandex.mapkit.MapKitFactory
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject


class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    var supportFragmentManager: FragmentManager? = null

    var activity: Activity? = null

    lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var menu: Menu
    private val groupsProduct = mutableListOf<GroupProducts>()
    val order by lazy {
        BasketEntity(
            items = mutableListOf()
        ).apply {
            this.bottomNavigationView = this@MainViewModel.bottomNavigationView
        }
    }

    fun getGroups() = menu.groups_products

    fun getGroupsProducts() = groupsProduct

    fun getModifiersType(id: String) = menu.groups_modifiers.find {
        it.id == id
    }

    fun geGroupsModifiers(childModifiers: List<ChildModifier>?) = childModifiers?.let {
        mutableListOf<Modifier>().also { list ->
            childModifiers.forEach { childModifier ->
                menu.modifiers.find { modifier ->
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

    fun getTerminals(): LiveData<LoadingState> {
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

    fun addToOrder(
        product: Product,
        hlebPosition: Int? = null,
        dobavitModifiers: List<MerchItem>? = null,
        removeModifiers: List<Modifier>? = null
    ) {
        order.add(
            BasketItem(
                product = product,
                modifiers = listOf(),
                removeModifiers = removeModifiers
            )
        )
    }

    fun makeOrder(isOrder: Boolean) {
        viewModelScope.launch {
            val users = repository.getUser()
            if(users.isEmpty()){
                mainFragment.openProfile(isOrder)
            }
            else{
                mainFragment.openOrderFragment()
            }
        }
    }

    private val _registrationState = MutableLiveData<LoadingState>()
    private val registrationState: LiveData<LoadingState>
        get() = _registrationState

    fun openAuthorisation(type: Authorisation) =
        apply { mainFragment.openAuthorisation(type) }

    fun registerUser(user: User): LiveData<LoadingState> {
        viewModelScope.launch {
            _registrationState.value = LoadingState.Loading
            try {
                val registrationResponse = repository.registerUser(user)

                val loginResponse = repository.loginUser(LoginRequest(
                    phone = registrationResponse.phone,
                    password = user.password!!
                ))

                repository.addUser(User(
                    phone = registrationResponse.phone,
                    name = registrationResponse.first_name,
                    token = loginResponse.auth_token
                    ))

                _registrationState.value = LoadingState.SuccessRegistration(registrationResponse)

            } catch (e: Exception) {
                when (e) {
                    is HttpException -> {
                        val t = e.response()?.errorBody()
                        t
                    }
                    else -> {
                        //Other errors like Network ...
                    }
                }
                _registrationState.value = LoadingState.Error(e.message)
            }

        }
        return registrationState
    }

    fun loginUser(user: User): LiveData<LoadingState> {
        viewModelScope.launch {
            _registrationState.value = LoadingState.Loading
            try {

                val loginResponse = repository.loginUser(LoginRequest(
                    phone = user.phone!!,
                    password = user.password!!
                ))

                val userInfo = repository.getUserInfo("Token ${loginResponse.auth_token}")

                repository.addUser(User(
                    phone = userInfo.phone,
                    name = userInfo.first_name,
                    token = loginResponse.auth_token
                ))

                _registrationState.value = LoadingState.SuccessLogin(loginResponse)

            } catch (e: Exception) {
                when (e) {
                    is HttpException -> {
                        val t = e.response()?.errorBody()
                        t
                    }
                    else -> {
                        //Other errors like Network ...
                    }
                }
                _registrationState.value = LoadingState.Error(e.message)
            }

        }
        return registrationState
    }

//   fun addUser(){
//       viewModelScope.launch {
//           val t = repository.addUser(User(
//               name = "name",
//               phone = "phone",
//               token = "token"
//           ))
//           t
//       }
//   }

    fun getUsers() = repository.getUsers()

    fun logOutUser(){
        viewModelScope.launch {
            val users = repository.getUser()
            repository.logOutUser("Token ${users.firstOrNull()?.token}")
            repository.deleteUser()
        }
    }

//    private fun convertErrorBody(throwable: HttpException): ErrorResponse? {
//        return try {
//            throwable.response()?.errorBody()?.source()?.let {
//               val t = it.buffer.re
//                null
//            }
//        } catch (exception: Exception) {
//            null
//        }
//    }

}