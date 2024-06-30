package com.himanshu.studybuddy.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun DeleteDialog(
    title: String,
    isDialogBoxOpen : Boolean,
    bodyText:String,
    onDismissButton: () -> Unit,
    onConfirmButton: () -> Unit
) {

    if(!isDialogBoxOpen)  return


    AlertDialog(
        onDismissRequest = onDismissButton,
        confirmButton = {
            TextButton(
                onClick = onConfirmButton,
            ) {
                Text(
                    text = "Confirm",
                    color = Color.Gray
                )
            }

        },
        dismissButton = {
            TextButton(
                onClick = onDismissButton,
            ) {
                Text(
                    text = "Cancel",
                    color = Color.Gray
                )
            }
        },

        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,

            )
        },
        text = {
           Text(text = bodyText)
        }
    )
}