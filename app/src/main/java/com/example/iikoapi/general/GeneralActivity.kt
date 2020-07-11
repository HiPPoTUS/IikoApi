package com.example.iikoapi.general

import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.iikoapi.R
import com.example.iikoapi.startapp.networking.menu
import com.example.iikoapi.utils.setBadges
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_general.*
import kotlinx.android.synthetic.main.fragment_menu.*
import kotlinx.android.synthetic.main.menu_recycler_view.*


lateinit var bottoNnavigationView : BottomNavigationView
var mappedMenu = menu.groupAndProducts("dish")
//general Activity

class GeneralActivity : AppCompatActivity() {

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
                R.id.basket ->{ selectedFragment = BasketFragment(this, navigationView); TAG = "4"}

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
    }


    override fun onBackPressed() {

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


}
