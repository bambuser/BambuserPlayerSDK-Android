package com.bambuser.liveshopping.player.ui

import androidx.lifecycle.ViewModel
import com.bambuser.player.io.LVSPlayerEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DemoCartViewModel : ViewModel() {

    /*
        Check out the readme file for the parameters of the functions

        CartProduct is the data class used in player sdk cart

        playerCartSyncItem is used to add, remove or change quantity of a item in the sdk cart
        fun playerCartSyncItem(...)

        playerCartInit is used to initialize the sdk cart with the initial state of your cart
        fun playerCartInit(...)
    */

    data class State(
        val products: List<String> = listOf()
    )

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    fun addProduct() {
        /*
        Changes to your cart
        ...
        and then update player sdk cart
        LVSPlayer.playerCartSyncItem(
            ...,
            isRemove = false
        )
        */
    }

    fun removeProduct() {
        /*
        Changes to your cart
        ...
        and then update player sdk cart
        LVSPlayer.playerCartSyncItem(
            ...,
            isRemove = true
        )
        */
    }

    fun increaseQuantity() {
        // Same as addProduct, make sure productId, colorVariantId and sizeVariantId are the same, and set the new quantity
    }

    fun decreaseQuantity() {
        // Same as addProduct, make sure productId, colorVariantId and sizeVariantId are the same, and set the new quantity
    }

    fun observePlayerEvents(events: Flow<LVSPlayerEvent>) {
        /*
        viewModelScope.launch {
            events.collect {
                if (it is LVSPlayerEvent.PlayerCartItemUpdated)
                    val item = it.item,
                    val isRemoved = it.isRemoved
                    Update local cart with this info
            }
        }
        */
    }

}
