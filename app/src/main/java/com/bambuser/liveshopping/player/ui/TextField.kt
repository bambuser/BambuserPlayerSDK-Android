/*
 * Created by Bambuser.
 * Copyright (c) 2023. All rights reserved.
 * Last modified 5/11/23, 3:54 PM
 */

package com.bambuser.liveshopping.player.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun ConfigurationTextField(
    onValueUpdated: (String) -> Unit,
    defaultValue: String,
    label: String,
    placeholderHelperText: String,
    errorOnEmpty: Boolean
) {
    val focusManager = LocalFocusManager.current
    val rememberTypedValue = remember { mutableStateOf(defaultValue) }

    OutlinedTextField(
        value = rememberTypedValue.value,
        onValueChange = {
            rememberTypedValue.value = it
            onValueUpdated(it)
        },
        placeholder = { Text(text = placeholderHelperText) },
        isError = rememberTypedValue.value.isBlank(),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        label = { Text(text = label) },
        singleLine = true,
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        supportingText = {
            if (errorOnEmpty && rememberTypedValue.value.isBlank()) {
                Text(
                    text = "This field cannot be blank",
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    )
}
