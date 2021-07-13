package com.example.iikoapi.views

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.IdRes
import androidx.navigation.*
import androidx.navigation.NavController.OnDestinationChangedListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.ref.WeakReference

/**
 * Manages the various graphs needed for a [BottomNavigationView].
 *
 * This sample is a workaround until the Navigation Component supports multiple back stacks.
 */
private var currentItem = 1

fun BottomNavigationView.setupWithNavControllerCustom(navController: NavController) {
    setupWithNavController(this, navController)
}

fun setupWithNavController(
    bottomNavigationView: BottomNavigationView,
    navController: NavController
) {

    val weakReference =
        WeakReference(bottomNavigationView)
    navController.addOnDestinationChangedListener(
        object : OnDestinationChangedListener {
            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination, arguments: Bundle?
            ) {
                val view = weakReference.get()
                if (view == null) {
                    navController.removeOnDestinationChangedListener(this)
                    return
                }
                val menu = view.menu
                var h = 0
                val size = menu.size()
                while (h < size) {
                    val item = menu.getItem(h)
                    destination.id = h
                    if (matchDestination(
                            destination,
                            item.itemId
                        )
                    ) {
                        item.isChecked = true
                    }
                    h++
                }
            }
        })

    bottomNavigationView.setOnNavigationItemSelectedListener { item ->
        onNavDestinationSelected(
            item,
            navController
        )
    }
}

    fun matchDestination(
        destination: NavDestination,
        @IdRes destId: Int
    ): Boolean {
        var currentDestination: NavDestination? = destination
        while (currentDestination!!.id != destId && currentDestination.parent != null) {
            currentDestination = currentDestination.parent
        }
        return currentDestination.id == destId
    }

    fun onNavDestinationSelected(
        item: MenuItem,
        navController: NavController
    ): Boolean {
        val nextItem = navController.currentDestination!!.parent
            ?.findNode(item.itemId)?.label.toString().toInt()
        if(currentItem == nextItem) return true

        val builder = NavOptions.Builder()
            .setLaunchSingleTop(true)

        var enterAnim = com.example.iikoapi.R.anim.enter_anim_left
        var exitAnim = com.example.iikoapi.R.anim.exit_anim_left
        var popEnterAnim = com.example.iikoapi.R.anim.enter_anim_right
        var popExitAnim = com.example.iikoapi.R.anim.exit_anim_right

        if(currentItem < nextItem){
            enterAnim = com.example.iikoapi.R.anim.enter_anim_right
            exitAnim = com.example.iikoapi.R.anim.exit_anim_right
            popEnterAnim = com.example.iikoapi.R.anim.enter_anim_left
            popExitAnim = com.example.iikoapi.R.anim.exit_anim_left
        }

        currentItem = navController.currentDestination!!.parent
            ?.findNode(item.itemId)?.label.toString().toInt()

        if (navController.currentDestination!!.parent
            ?.findNode(item.itemId) is ActivityNavigator.Destination
        ) {
            builder.setEnterAnim(enterAnim)
                .setExitAnim(exitAnim)
                .setPopEnterAnim(popEnterAnim)
                .setPopExitAnim(popExitAnim)
        } else {
            builder.setEnterAnim(enterAnim)
                .setExitAnim(exitAnim)
                .setPopEnterAnim(popEnterAnim)
                .setPopExitAnim(popExitAnim)
        }
        if (item.order and Menu.CATEGORY_SECONDARY == 0) {
            builder.setPopUpTo(
                findStartDestination(navController.graph)?.id!!,
                false
            )
        }
        val options = builder.build()
        return try {
            //TODO provide proper API instead of using Exceptions as Control-Flow.
            navController.navigate(item.itemId, null, options)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }

fun findStartDestination(graph: NavGraph): NavDestination? {
    var startDestination: NavDestination? = graph
    while (startDestination is NavGraph) {
        val parent = startDestination
        startDestination = parent.findNode(parent.startDestination)
    }
    return startDestination
}
