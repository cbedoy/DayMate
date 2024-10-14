package com.cb.meapps.presentation.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun CommonInputField(
    label: String,
    placeholder: String,
    currentValue: String,
    inputType: InputType,
    onValueChange: (String) -> Unit
) {

    val onValueChange : (String) -> Unit = { newValue ->
        when (inputType) {
            InputType.NUMBER -> {
                if (newValue.all { it.isDigit() }) {
                    onValueChange(newValue)
                }
            }
            InputType.TEXT -> {
                onValueChange(newValue)
            }
            InputType.EMAIL -> {
                if (newValue.contains("@")) {
                    onValueChange(newValue)
                }
            }
            is InputType.NumberPicker -> {}
        }
    }

    val keyboardOptions = when (inputType) {
        InputType.NUMBER -> KeyboardOptions(keyboardType = KeyboardType.Number)
        InputType.TEXT -> KeyboardOptions(keyboardType = KeyboardType.Text)
        InputType.EMAIL -> KeyboardOptions(keyboardType = KeyboardType.Email)
        is InputType.NumberPicker -> KeyboardOptions.Default
    }

    val isReadyOnly = when (inputType) {
        is InputType.NumberPicker -> true
        else -> false
    }

    var showDialog by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = currentValue,
            onValueChange = { newValue -> onValueChange(newValue) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp).apply {
                    clickable {
                        if (isReadyOnly) {
                            showDialog = true
                        }
                    }
                },
            label = {
                Text(
                    label,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colorScheme.primary,
                )
            },
            placeholder = {
                Text(
                    placeholder,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colorScheme.secondary,
                )
            },
            readOnly = isReadyOnly,
            keyboardOptions = keyboardOptions,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.secondary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary
            )
        )

        if (showDialog) {
            NumberPickerDialog(
                initialValue = currentValue.toIntOrNull() ?: 0,
                onDismiss = { showDialog = false },
                onValueSelected = { newValue ->
                    onValueChange(newValue.toString())
                    showDialog = false
                },
                range = if (inputType is InputType.NumberPicker) {
                    inputType.range
                } else {
                    return
                }
            )
        }
    }
}

@Composable
private fun NumberPickerDialog(
    initialValue: Int,
    onDismiss: () -> Unit,
    onValueSelected: (Int) -> Unit,
    range: IntRange
) {
    var selectedValue by remember { mutableStateOf(initialValue) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Pick a number") },
        text = {
            Column {
                Text("Selected: $selectedValue")
                Slider(
                    value = selectedValue.toFloat(),
                    onValueChange = { newValue ->
                        selectedValue = newValue.toInt()
                    },
                    valueRange = range.first.toFloat()..range.last.toFloat(),
                    steps = range.last - range.first
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onValueSelected(selectedValue)
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }
    )
}

sealed class InputType {
    data object TEXT : InputType()
    data object NUMBER : InputType()
    data object EMAIL : InputType()

    data class NumberPicker(val range: IntRange): InputType()
}