/*
 * Created by Bambuser.
 * Copyright (c) 2023. All rights reserved.
 * Last modified 8/24/23, 2:11 PM
 */

package com.example.player.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.lvssdkplayerdemo.defaultConfiguration

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
    enableStartPlayerButton: Boolean,
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

                ConfigurationTextField(onShowIdUpdated, "")

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
            }
        }

        Button(
            onClick = onStartPlayer,
            enabled = enableStartPlayerButton
        ) {
            Text(text = "Start Player")
        }
    }
}
