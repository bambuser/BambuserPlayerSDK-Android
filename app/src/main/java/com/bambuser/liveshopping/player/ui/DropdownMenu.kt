/*
 * Created by Bambuser.
 * Copyright (c) 2023. All rights reserved.
 * Last modified 9/15/23, 4:29 PM
 */

package com.bambuser.liveshopping.player.ui

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> DropdownMenu(
    defaultValue: T,
    allValues: List<T>,
    label: String,
    onValueChange: (T) -> Unit,
    modifier: Modifier = Modifier,
    valueToString: (T) -> String = { it.toString() },
) {
    var expanded by remember { mutableStateOf(false) }
    var currentValue by remember(defaultValue) { mutableStateOf(defaultValue) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier,
    ) {
        OutlinedTextField(
            readOnly = true,
            value = valueToString(currentValue),
            onValueChange = { },
            label = { Text(text = label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )

        androidx.compose.material3.DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxHeight(0.5F)
        ) {
            allValues.forEach {
                DropdownMenuItem(
                    text = { Text(text = valueToString(it)) },
                    onClick = {
                        onValueChange(it)
                        currentValue = it
                        expanded = false
                    })
                Divider()
            }
        }
    }
}
