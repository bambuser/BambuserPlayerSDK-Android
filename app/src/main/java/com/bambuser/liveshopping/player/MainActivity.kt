/*
 * Created by Bambuser.
 * Copyright (c) 2023. All rights reserved.
 * Last modified 8/24/23, 2:03 PM
 */

package com.bambuser.liveshopping.player

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.bambuser.player.Configuration
import com.bambuser.player.LVSPlayer
import com.bambuser.player.LVSPlayerError
import com.bambuser.player.observer.EventObserver
import com.bambuser.player.observer.LVSPlayerEvent
import com.bambuser.liveshopping.player.theme.MyApplicationTheme
import com.bambuser.liveshopping.player.ui.ConfigurationScreen

// Example of a LVS player configuration
val defaultConfiguration = Configuration(
    isEUServer = false,
    showHighlightedProducts = true,
    showChat = true,
    showProductsOnEndCurtain = true,
    enablePictureInPicture = true,
    enableProductDetailsPage = true,
    showShoppingCart = true,
    showLikes = true
)

class MainActivity : ComponentActivity(), EventObserver {

    companion object {
        const val TAG = "MainActivity"
    }

    private var isDialogVisible = false

    override fun onResume() {
        super.onResume()
        if (!isDialogVisible) {
            ErrorHolder.error?.let {
                isDialogVisible = true
                AlertDialog.Builder(this)
                    .setTitle("Something went wrong")
                    .setMessage("The player threw the following error: ${it.message}")
                    .setCancelable(false)
                    .setPositiveButton("Ok") { dialog, _ ->
                        dialog.dismiss()
                        ErrorHolder.error = null
                        isDialogVisible = false
                    }
                    .create()
                    .show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface {
                    val showId = remember { mutableStateOf("") }
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
                        enableStartPlayerButton = showId.value.isNotBlank()
                    ) {
                        ErrorHolder.error = null

                        Configuration(
                            isEUServer = isEUServer.value,
                            showHighlightedProducts = showHighlightedProducts.value,
                            showChat = showChat.value,
                            showProductsOnEndCurtain = showProductsOnEndCurtain.value,
                            enablePictureInPicture = enablePiP.value,
                            enableProductDetailsPage = enablePDP.value,
                            showShoppingCart = showCart.value,
                            showLikes = showLikes.value
                        ).also {
                            LVSPlayer.startActivity(
                                context = this,
                                showId = showId.value,
                                configuration = it,
                                eventObserver = this
                            )
                        }
                    }
                }
            }
        }
    }

    // Callback for events received from the LVS Player
    override fun onEvent(lvsPlayerEvent: LVSPlayerEvent) {
        Log.d(TAG, "events: $lvsPlayerEvent")
        if (lvsPlayerEvent is LVSPlayerEvent.OnError) {
            ErrorHolder.error = lvsPlayerEvent.lvsPlayerError
        }
    }
}

// Holder used to display the last received LVS player error
object ErrorHolder {
    var error: LVSPlayerError? = null
}
