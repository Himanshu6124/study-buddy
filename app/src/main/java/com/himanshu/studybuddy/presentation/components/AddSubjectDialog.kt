package com.himanshu.studybuddy.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.himanshu.studybuddy.domain.model.Subject

@Composable
fun AddSubjectDialog(
    isDialogBoxOpen : Boolean,
    selectedSubject: String,
    selectedGoalHours: String,
    onSubjectNameChange: (String) -> Unit,
    onGoalHoursChange: (String) -> Unit,
    selectedColor: List<Color>,
    onColorChange: (List<Color>) -> Unit,
    onDismissButton: () -> Unit,
    onConfirmButton: () -> Unit
) {

    if(!isDialogBoxOpen)  return

    var subjectNameError by rememberSaveable { mutableStateOf("") }
    var goalHoursError by rememberSaveable { mutableStateOf("") }


    subjectNameError = when {
        selectedSubject.isBlank() -> "Please enter subject name."
        selectedSubject.length < 2 -> "Subject name is too short."
        selectedSubject.length > 20 -> "Subject name is too long."
        else -> ""
    }
    goalHoursError = when {
        selectedGoalHours.isBlank() -> "Please enter goal study hours."
        selectedGoalHours.toFloatOrNull() == null -> "Invalid number."
        selectedGoalHours.toFloat() < 1f -> "Please set at least 1 hour."
        selectedGoalHours.toFloat() > 1000f -> "Please set a maximum of 1000 hours."
        else -> ""
    }

    AlertDialog(
        onDismissRequest = onDismissButton,
        confirmButton = {
            TextButton(
                onClick = onConfirmButton,
                enabled = subjectNameError == "" && goalHoursError == ""
            ) {
                Text(
                    text = "Confirm",
                    color = Color.DarkGray
                )
            }

        },
        dismissButton = {
            TextButton(
                onClick = onDismissButton,
                enabled = subjectNameError == "" && goalHoursError == ""
            ) {
                Text(
                    text = "Cancel",
                    color = Color.Gray
                )
            }
        },

        title = {
            Text(
                text = "Add/Update subject",
                style = MaterialTheme.typography.headlineMedium
            )
        },
        text = {
            Column {
                ColorBox(selectedColor, onColorChange)
                OutlinedTextField(
                    modifier = Modifier.padding(vertical = 5.dp),
                    value = selectedSubject, onValueChange = onSubjectNameChange,
                    supportingText = { Text(text = subjectNameError)},
                    isError = subjectNameError != "",
                )

                OutlinedTextField(
                    value = selectedGoalHours,
                    onValueChange = onGoalHoursChange,
                    modifier = Modifier.padding(vertical = 5.dp),
                    supportingText = { Text(text = goalHoursError)},
                    isError = goalHoursError != "",
                )
            }
        }
    )
}

@Composable
fun ColorBox(selectedColor: List<Color>, onColorChange: (List<Color>) -> Unit) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Subject.subjectCardColors.forEach { color ->
            Box(
                modifier = Modifier
                    .padding(12.dp)
                    .size(25.dp)
                    .clip(CircleShape)
                    .background(Brush.verticalGradient(color))
                    .border(
                        width = 2.dp,
                        color = if (color == selectedColor) Color.Red else Color.Gray,
                        shape = CircleShape
                    )
                    .clickable { onColorChange(color) }
            )
        }
    }
}