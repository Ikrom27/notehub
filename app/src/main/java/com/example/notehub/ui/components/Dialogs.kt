package com.example.notehub.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.notehub.constants.LABEL_CONFIRM
import com.example.notehub.constants.LABEL_CREATE_NEW_FOLDER
import com.example.notehub.constants.LABEL_DISMISS
import com.example.notehub.constants.LABEL_FOLDER_NAME
import com.example.notehub.constants.TEXT_INCORRECT_FOLDER_NAME
import com.example.notehub.utils.isValidInput

@Composable
fun SetNameDialog(
    defaultName: String = "",
    onDismissRequest: () -> Unit,
    confirmButton: (String) -> Unit
) {
    var userInput by remember { mutableStateOf(defaultName) }
    AlertDialog(
        title = {
            Text(text = LABEL_CREATE_NEW_FOLDER)
        },
        text = {
            Column {
                OutlinedTextField(
                    label = { Text(text = LABEL_FOLDER_NAME) },
                    value = userInput,
                    onValueChange = { userInput = it },
                    singleLine = true
                )
            }
        },
        onDismissRequest = {
            onDismissRequest()
            userInput = ""
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                    userInput = ""
                }
            ) {
                Text(LABEL_DISMISS)
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    confirmButton(userInput)
                    userInput = ""
                    onDismissRequest()
                }
            ) {
                Text(LABEL_CONFIRM)
            }
        },
    )
}