/*
 * Created by Bambuser.
 * Copyright (c) 2023. All rights reserved.
 * Last modified 11/9/23, 9:03 AM
 */

package com.bambuser.liveshopping.player.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.bambuser.liveshopping.player.defaultConfiguration
import com.bambuser.player.LVSPlayer
import com.bambuser.player.io.LVSPlayerInputAction

@Composable
fun ConfigurationScreen(
    onShowIdUpdated: (String) -> Unit,
    onIsEUServerUpdated: (Boolean) -> Unit,
    onShowHighlightedProductsUpdated: (Boolean) -> Unit,
    onShowChatUpdated: (Boolean) -> Unit,
    onShowProductsOnEndCurtainUpdated: (Boolean) -> Unit,
    onEnablePiPUpdated: (Boolean) -> Unit,
    onEnablePDPUpdated: (Boolean) -> Unit,
    onShowCartUpdated: (Boolean) -> Unit,
    onShowLikesUpdated: (Boolean) -> Unit,
    onPreferredLocaleUpdated: (String) -> Unit,
    onShowNumberOfViewersUpdated: (Boolean) -> Unit,
    enableStartPlayerButton: Boolean,
    playerId: String?,
    isEUServer: Boolean,
    onStartPlayer: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "LVS Player SDK Demo", style = MaterialTheme.typography.titleLarge)

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation()
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {

                ConfigurationTextField(
                    onValueUpdated = onShowIdUpdated,
                    defaultValue = "",
                    label = "SHOW ID",
                    placeholderHelperText = "Put your show id in this field",
                    errorOnEmpty = true
                )

                ToggleButton(
                    text = "Is EU Server?",
                    onValueUpdated = onIsEUServerUpdated,
                    defaultState = defaultConfiguration.isEUServer
                )
                ToggleButton(
                    text = "Show highlighted products",
                    onValueUpdated = onShowHighlightedProductsUpdated,
                    defaultState = defaultConfiguration.showHighlightedProducts
                )
                ToggleButton(
                    text = "Show chat",
                    onValueUpdated = onShowChatUpdated,
                    defaultState = defaultConfiguration.showChat
                )
                ToggleButton(
                    text = "Show products on end curtain",
                    onValueUpdated = onShowProductsOnEndCurtainUpdated,
                    defaultState = defaultConfiguration.showProductsOnEndCurtain
                )
                ToggleButton(
                    text = "Enable picture in picture",
                    onValueUpdated = onEnablePiPUpdated,
                    defaultState = defaultConfiguration.enablePictureInPicture
                )
                ToggleButton(
                    text = "Enable product details page",
                    onValueUpdated = onEnablePDPUpdated,
                    defaultState = defaultConfiguration.enableProductDetailsPage
                )
                ToggleButton(
                    text = "Show shopping cart",
                    onValueUpdated = onShowCartUpdated,
                    defaultState = defaultConfiguration.showShoppingCart
                )
                ToggleButton(
                    text = "Show likes",
                    onValueUpdated = onShowLikesUpdated,
                    defaultState = defaultConfiguration.showLikes
                )

                ToggleButton(
                    text = "Show number of viewers",
                    onValueUpdated = onShowNumberOfViewersUpdated,
                    defaultState = defaultConfiguration.showNumberOfViewers
                )

                ConfigurationTextField(
                    onValueUpdated = onPreferredLocaleUpdated,
                    defaultValue = defaultConfiguration.preferredLocale,
                    label = "LOCALE NAME",
                    placeholderHelperText = "Type your locale here",
                    errorOnEmpty = false
                )
            }
        }

        Button(
            onClick = onStartPlayer,
            enabled = enableStartPlayerButton
        ) {
            Text(text = "Start Player")
        }

        val context = LocalContext.current.applicationContext
        Button(
            onClick = {
                LVSPlayer.trackPurchase(
                    context,
                    isEUServer,
                    "123",
                    19.99,
                    listOf("1", "2", "3"),
                    "SEK",
                    "Sweden"
                )
            },
            enabled = enableStartPlayerButton
        ) {
            Text(text = "Track purchase")
        }
        Button(
            onClick = {
                LVSPlayer.clearTrackingData(context, isEUServer)
            },
            enabled = enableStartPlayerButton
        ) {
            Text(text = "Clear tracking data")
        }

        playerId?.let {
            Divider(modifier = Modifier.padding(vertical = 32.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                var inputAction: LVSPlayerInputAction by remember(it) {
                    mutableStateOf(
                        LVSPlayerInputAction.Minimize(it)
                    )
                }
                val inputActions by remember(it) { mutableStateOf(LVSPlayerInputAction.toList(it)) }

                DropdownMenu(
                    defaultValue = inputAction,
                    allValues = inputActions,
                    label = "Input action",
                    onValueChange = { inputAction = it },
                    valueToString = { it.name },
                    modifier = Modifier.fillMaxWidth(0.5F)
                )

                Button(onClick = {
                    // This will attempt to perform inputAction on the player with the given playerId
                    // If no such player exist then no action will be performed
                    LVSPlayer.performAction(inputAction)
                }) {
                    Text(text = "Perform action")
                }
            }
        }
    }
}
