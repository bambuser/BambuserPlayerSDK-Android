/*
 * Created by Bambuser.
 * Copyright (c) 2023. All rights reserved.
 * Last modified 9/29/23, 11:27 AM
 */

package com.bambuser.liveshopping.player

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.bambuser.liveshopping.Parameters
import com.bambuser.liveshopping.ParcelableConfiguration
import com.bambuser.liveshopping.player.theme.MyApplicationTheme
import com.bambuser.liveshopping.player.ui.ConfigurationScreen
import com.bambuser.player.Configuration
import com.bambuser.player.LVSPlayer
import com.bambuser.player.LVSPlayerError
import com.bambuser.player.io.EventObserver
import com.bambuser.player.io.LVSPlayerEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainActivity : ComponentActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    private var isDialogVisible = false
    private val eventObserver = EventObserverExample

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface {
                    val showId = remember { mutableStateOf("vAtJH3xevpYTLnf1oHao") }
                    val isEUServer = remember { mutableStateOf(defaultConfiguration.isEUServer) }
                    val showHighlightedProducts =
                        remember { mutableStateOf(defaultConfiguration.showHighlightedProducts) }
                    val showChat = remember { mutableStateOf(defaultConfiguration.showChat) }
                    val showProductsOnEndCurtain =
                        remember { mutableStateOf(defaultConfiguration.showProductsOnEndCurtain) }
                    val enablePiP =
                        remember { mutableStateOf(defaultConfiguration.enablePictureInPicture) }
                    val enablePDP =
                        remember { mutableStateOf(defaultConfiguration.enableProductDetailsPage) }
                    val showCart =
                        remember { mutableStateOf(defaultConfiguration.showShoppingCart) }
                    val showLikes = remember { mutableStateOf(defaultConfiguration.showLikes) }
                    val preferredLocale =
                        remember { mutableStateOf(defaultConfiguration.preferredLocale) }
                    val showNumberOfViewers =
                        remember { mutableStateOf(defaultConfiguration.showNumberOfViewers) }
                    val playerId = remember { mutableStateOf<String?>(null) }

                    ConfigurationScreen(
                        onShowIdUpdated = { showId.value = it },
                        onIsEUServerUpdated = { isEUServer.value = it },
                        onShowHighlightedProductsUpdated = { showHighlightedProducts.value = it },
                        onShowChatUpdated = { showChat.value = it },
                        onShowProductsOnEndCurtainUpdated = { showProductsOnEndCurtain.value = it },
                        onEnablePiPUpdated = { enablePiP.value = it },
                        onEnablePDPUpdated = { enablePDP.value = it },
                        onShowCartUpdated = { showCart.value = it },
                        onShowLikesUpdated = { showLikes.value = it },
                        onPreferredLocaleUpdated = { preferredLocale.value = it },
                        onShowNumberOfViewersUpdated = { showNumberOfViewers.value = it },
                        enableStartPlayerButton = showId.value.isNotBlank(),
                        playerId = playerId.value,
                        isEUServer = isEUServer.value,
                        onStartPlayerAsCompose = {
                            val parameters = Parameters(
                                showId = showId.value,
                                configuration = ParcelableConfiguration(
                                    isEUServer = isEUServer.value,
                                    showHighlightedProducts = showHighlightedProducts.value,
                                    showChat = showChat.value,
                                    showProductsOnEndCurtain = showProductsOnEndCurtain.value,
                                    enablePictureInPicture = enablePiP.value,
                                    enableProductDetailsPage = enablePDP.value,
                                    showShoppingCart = showCart.value,
                                    showLikes = showLikes.value,
                                    preferredLocale = preferredLocale.value,
                                    showNumberOfViewers = showNumberOfViewers.value,
                                    conversionTrackingTTLDays = 1,
                                )
                            )
                            Intent(this, ComposeHostActivity::class.java).apply {
                                putExtra(ComposeHostActivity.KEY_PARAMETERS, parameters)
                                startActivity(this)
                            }
                        },
                        onStartPlayer = {
                            EventObserverExample.error = null

                            Configuration(
                                isEUServer = isEUServer.value,
                                showHighlightedProducts = showHighlightedProducts.value,
                                showChat = showChat.value,
                                showProductsOnEndCurtain = showProductsOnEndCurtain.value,
                                enablePictureInPicture = enablePiP.value,
                                enableProductDetailsPage = enablePDP.value,
                                showShoppingCart = showCart.value,
                                showLikes = showLikes.value,
                                preferredLocale = preferredLocale.value,
                                showNumberOfViewers = showNumberOfViewers.value,
                                conversionTrackingTTLDays = 1,
                            ).also {
                                playerId.value = LVSPlayer.startActivity(
                                    context = this,
                                    showId = showId.value,
                                    configuration = it,
                                    eventObserver = eventObserver,
                                    // Provide your own player id here if you wish, otherwise one will be generated upon starting the activity
                                )
                                /*
                                CART TUTORIAL:

                                First initialize the player with the products that are available in the cart.
                                LVSPlayer.playerCartInit(
                                    ...,
                                    pass in products that are already in your cart
                                )
                                Make sure to observe the player events to update the cart when the player emits events.
                                demoCartViewModel.observePlayerEvents(LVSPlayer.observeEvents(playerId.value))
                                */

                                /*
                                In your demo cart view, you will be listening to the products from the CartViewModel,
                                You can observe the cart sdk events there and adjust your products in the cart accordingly.
                                Also, when you make changes in your cart, you will send events to the player to update the cart.
                                DemoCart(state.products, viewModel::increaseQuantity, viewModel::decreaseQuantity, viewModel::removeProduct, viewModel::addProduct)

                                In this example the viewModel will be sending the events, please see DemoCartViewModel.kt
                                */
                            }
                        }
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isDialogVisible) {
            EventObserverExample.error?.let {
                isDialogVisible = true
                AlertDialog.Builder(this)
                    .setTitle("Something went wrong")
                    .setMessage("The player threw the following error: ${it.message}")
                    .setCancelable(false)
                    .setPositiveButton("Ok") { dialog, _ ->
                        dialog.dismiss()
                        EventObserverExample.error = null
                        isDialogVisible = false
                    }
                    .create()
                    .show()
            }
        }
    }
}

// Example of a LVS player configuration
val defaultConfiguration = Configuration(
    isEUServer = false,
    showHighlightedProducts = true,
    showChat = true,
    showProductsOnEndCurtain = true,
    enablePictureInPicture = true,
    enableProductDetailsPage = true,
    showShoppingCart = true,
    showLikes = true,
    preferredLocale = "en-US",
    showNumberOfViewers = true,
)

/**
 * This is an example of how you can intercept events from the LVS Player SDK.
 * Here, we have an object that overrides the EventObserver interface and stores the latest emitted
 * error.
 *
 * An alternative way to intercept the events is to listen directly to an events-flow from the LVS Player.
 * It can be reached in the following way:
 * val lvsPlayerEventsFlow = LVSPlayer.observeEvents(playerId)
 */
object EventObserverExample : EventObserver {
    var error: LVSPlayerError? = null
    private val _errorStateFlow = MutableStateFlow<LVSPlayerError?>(null)
    var errorStateFlow: Flow<LVSPlayerError?> = _errorStateFlow.asStateFlow()

    override fun onEvent(lvsPlayerEvent: LVSPlayerEvent) {
        Log.d(MainActivity.TAG, "events: $lvsPlayerEvent")
        if (lvsPlayerEvent is LVSPlayerEvent.OnError) {
            error = lvsPlayerEvent.lvsPlayerError
            _errorStateFlow.value = lvsPlayerEvent.lvsPlayerError
        }
    }
}
