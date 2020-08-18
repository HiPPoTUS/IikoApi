package com.example.iikoapi.general

import Group
import Product
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.iikoapi.R
import com.example.iikoapi.startapp.datatype.Post3dsRequestArgs
import com.example.iikoapi.startapp.datatype.Transaction
import com.example.iikoapi.startapp.menu
import com.example.iikoapi.startapp.networking.CP
import com.example.iikoapi.startapp.networking.cp_NetworkService
import com.example.iikoapi.utils.setBadges
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_general.*
import kotlinx.android.synthetic.main.on_order_pop_up.*
import ru.cloudpayments.sdk.three_ds.ThreeDSDialogListener
import ru.cloudpayments.sdk.three_ds.ThreeDsDialogFragment

lateinit var bottoNnavigationView : BottomNavigationView
lateinit var men:List<Group>
lateinit var mod:List<Group>
lateinit var menu_prods:MutableMap<String,List<Product>>
lateinit var mods_prods:MutableMap<String,List<Product>>
//general Activity

class GeneralActivity : AppCompatActivity(), ThreeDSDialogListener {
    val cp = CP(cp_NetworkService.instance!!)

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general)
        bottoNnavigationView = findViewById(R.id.navigationView)
        setBadges()

        if (intent.getIntExtra("back_from", 0) != 0)
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, MenuFragment(intent.getIntExtra("back_from", 0), this), "1").commit()
        else
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, MenuFragment(0, this), "1").commit()



        bottoNnavigationView.setOnNavigationItemSelectedListener { item ->
            lateinit var selectedFragment : Fragment
            lateinit var TAG :String
            var fragmentTransaction = supportFragmentManager.beginTransaction()

            when (item.itemId) {
                R.id.acces_menu -> {selectedFragment = MenuFragment(0, this); TAG = "1"}
                R.id.contacts -> {selectedFragment = ContactsFragment(this); TAG = "2"}
                R.id.profile ->{ selectedFragment = ProfileFragment(); TAG = "3"}
                R.id.basket ->{ selectedFragment = BasketFragment(this, navigationView, payment); TAG = "4"}

            }

            var fragmentTag = supportFragmentManager.findFragmentById(R.id.fragment_container)!!.tag
            if(fragmentTag != TAG) {
                if(fragmentTag!!.toInt() > TAG.toInt())
                    fragmentTransaction.setCustomAnimations(R.anim.enter_anim_left,R.anim.exit_anim_left)
                else
                    fragmentTransaction.setCustomAnimations(R.anim.enter_anim_right,R.anim.exit_anim_right)
                fragmentTransaction.replace(R.id.fragment_container, selectedFragment, TAG).commit()
            }

            true
        }
        var tmp = menu.get_groups().toMutableList()
        val divergent = tmp.find { it.name=="Все Допы" }!!
        tmp.remove(divergent)
        men = tmp
        val tmp2 = menu.get_groups(isIncluded = false)
        mod  = List<Group>(tmp2.size+1){if (it==0) divergent else tmp2[it-1]}
        menu_prods = menu.get_groups_prods(men)
        mods_prods = menu.get_groups_prods(mod,true)
        mods_prods.put(divergent.id!!, menu.get_group_prods(divergent))
    }


    override fun onBackPressed() {

        if(BottomSheetBehavior.from(payment).state == BottomSheetBehavior.STATE_EXPANDED){
            BottomSheetBehavior.from(payment).state = BottomSheetBehavior.STATE_COLLAPSED
            return
        }

        if(supportFragmentManager.findFragmentById(R.id.fragment_container) == supportFragmentManager.findFragmentByTag("1")) {
            this.finishAffinity()
            super.onBackPressed()}
        else
            supportFragmentManager.
            beginTransaction().
            setCustomAnimations(R.anim.enter_anim_left,R.anim.exit_anim_left).
            replace(R.id.fragment_container, MenuFragment(0, this), "1").
            commit()

        navigationView.menu.getItem(0).isChecked = true
    }

    override fun onResume() {
        super.onResume()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    override fun onAuthorizationCompleted(md: String?, paRes: String?) {
        val tmp = post3ds(cp, Post3dsRequestArgs(md,paRes)).execute().get()
        Log.d("transms", tmp.cardHolderMessage.toString() )
    }

    override fun onAuthorizationFailed(html: String?) {
        Toast.makeText(this,"AuthorizationFailed: " + html, Toast.LENGTH_LONG).show();
    }

    fun show3DS(trans: Transaction) {
        ThreeDsDialogFragment.newInstance(trans.acsUrl,
            trans.id,
            trans.paReq)
            .show(getSupportFragmentManager(), "3DS");
    }
}
