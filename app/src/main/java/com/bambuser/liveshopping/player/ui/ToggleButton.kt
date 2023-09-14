/*
 * Created by Bambuser.
 * Copyright (c) 2023. All rights reserved.
 * Last modified 5/11/23, 3:23 PM
 */

package com.bambuser.liveshopping.player.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ToggleButton(text: String, onValueUpdated: (Boolean) -> Unit, defaultState: Boolean) {
    val isChecked = remember { mutableStateOf(defaultState) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = text, style = MaterialTheme.typography.bodyMedium)
        Switch(
            checked = isChecked.value,
            onCheckedChange = {
                isChecked.value = it
                onValueUpdated(it)
            }
        )
    }
}
