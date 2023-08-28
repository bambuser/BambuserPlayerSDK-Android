/*
 * Created by Bambuser.
 * Copyright (c) 2023. All rights reserved.
 * Last modified 5/11/23, 3:54 PM
 */

package com.example.player.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun ConfigurationTextField(onShowIdUpdated: (String) -> Unit, defaultValue: String) {
    val focusManager = LocalFocusManager.current
    val rememberShowId = remember { mutableStateOf(defaultValue) }

    OutlinedTextField(
        value = rememberShowId.value,
        onValueChange = {
            rememberShowId.value = it
            onShowIdUpdated(it)
        },
        placeholder = { Text(text = "Put your show id in this field") },
        isError = rememberShowId.value.isBlank(),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        label = { Text(text = "SHOW ID") },
        singleLine = true,
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        supportingText = {
            if (rememberShowId.value.isBlank()) {
                Text(
                    text = "This field cannot be blank",
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    )
}