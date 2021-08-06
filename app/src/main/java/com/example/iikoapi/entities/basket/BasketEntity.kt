package com.example.iikoapi.entities.basket

import com.example.iikoapi.R
import com.google.android.material.bottomnavigation.BottomNavigationView

data class BasketEntity(
    val items: MutableList<BasketItemEntity>,
) {
    var allPrice = 0L

    lateinit var bottomNavigationView: BottomNavigationView

    init {
        items.forEach {
            allPrice += it.basketItem.product.price ?: 0L * it.count
        }
    }

    val isEmpty
        get() = items.size == 0

    fun add(basketItem: BasketItem) {
        if (items.isEmpty()) {
            items.add(
                BasketItemEntity(
                    basketItem = basketItem,
                    count = 1
                )
            )
        } else {
            var t = -1
            items.forEachIndexed { i, item ->
                if (item.basketItem == basketItem) {
                    t = i
                    return@forEachIndexed
                }
            }
            if (t != -1) {
                items[t].count++
            } else {
                items.add(
                    0,
                    BasketItemEntity(
                        basketItem = basketItem,
                        count = 1
                    )
                )
            }
        }
        allPrice += basketItem.product.price ?: 0

        updateBadge()
    }

    fun updateItem(position: Int, plus: Boolean = true) {
        if (plus) {
            items[position].count++
            allPrice += items[position].basketItem.product.price ?: 0
        } else {
            items[position].count--
            allPrice -= items[position].basketItem.product.price ?: 0
        }

        updateBadge()
    }

    private fun updateBadge() {
        val badge = bottomNavigationView.getOrCreateBadge(R.id.basketFragment)
        val number = if (items.isEmpty()) {
            0
        } else {
            var t = 0
            items.forEach {
                t += it.count
            }
            t
        }
        badge.number = number
        badge.isVisible = number > 0
    }
}