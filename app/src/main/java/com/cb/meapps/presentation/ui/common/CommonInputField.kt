package com.cb.meapps.presentation.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cb.meapps.R
import com.cb.meapps.presentation.ui.screens.dialog.NumberPickerDialog
import com.cb.meapps.presentation.ui.screens.dialog.ValuePickerDialog

@Composable
fun CommonInputField(
    label: String,
    placeholder: String,
    currentValue: String,
    inputType: InputType,
    enabled: Boolean = true,
    leadingIconId: Int? = null,
    onValueChange: (String) -> Unit
) {

    val onChanged : (String) -> Unit = { newValue ->
        when (inputType) {
            InputType.Number -> {
                if (newValue.all { it.isDigit() }) {
                    onValueChange(newValue)
                }
            }
            InputType.Decimal -> {
                val regex = Regex("^[0-9]*\\.?[0-9]*$")
                if (regex.matches(newValue)) {
                    onValueChange(newValue)
                }
            }
            InputType.Text -> {
                onValueChange(newValue)
            }
            InputType.Email -> {
                if (newValue.contains("@")) {
                    onValueChange(newValue)
                }
            }
            is InputType.NumberPicker, is InputType.ValuePicker -> {
                onValueChange(newValue)
            }
        }
    }

    val keyboardOptions = when (inputType) {
        InputType.Number -> KeyboardOptions(keyboardType = KeyboardType.Number)
        InputType.Decimal -> KeyboardOptions(keyboardType = KeyboardType.Decimal)
        InputType.Text -> KeyboardOptions(keyboardType = KeyboardType.Text)
        InputType.Email -> KeyboardOptions(keyboardType = KeyboardType.Email)
        is InputType.NumberPicker, is InputType.ValuePicker -> KeyboardOptions.Default
    }

    val isReadyOnly = when (inputType) {
        is InputType.NumberPicker, is InputType.ValuePicker -> true
        else -> false
    }

    var showDialog by remember { mutableStateOf(false) }

    Column {
        Box {
            OutlinedTextField(
                value = currentValue,
                onValueChange = { newValue -> onChanged(newValue) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
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
                leadingIcon = {
                    leadingIconId?.let {
                        Icon(
                            painter = painterResource(id = leadingIconId),
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                },
                enabled = enabled,
                readOnly = isReadyOnly,
                keyboardOptions = keyboardOptions,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.secondary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary
                )
            )
            if (isReadyOnly) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(64.dp)
                    .clickable {
                        showDialog = true
                    })
            }
        }

        if (showDialog) {
            when (inputType) {
                is InputType.NumberPicker -> {
                    NumberPickerDialog(
                        initialValue = currentValue.toIntOrNull() ?: 0,
                        onDismiss = { showDialog = false },
                        onValueSelected = { newValue ->
                            onValueChange(newValue.toString())
                            showDialog = false
                        },
                        range = inputType.range
                    )
                }

                is InputType.ValuePicker -> {
                    ValuePickerDialog(
                        initialValue = inputType.selectedIndex,
                        onDismiss = { showDialog = false },
                        values = inputType.values,
                        onValueSelected = { newValue ->
                            onValueChange(inputType.values[newValue])
                            showDialog = false
                        }
                    )
                }

                else -> {

                }
            }
        }
    }
}

sealed class InputType {
    data object Text : InputType()
    data object Number : InputType()
    data object Decimal : InputType()
    data object Email : InputType()

    data class NumberPicker(val range: IntRange): InputType()

    data class ValuePicker(
        val selectedIndex: Int = 0,
        val values: List<String>
    ): InputType()
}

@Preview
@Composable
private fun PreviewCommonInputField() {
    Surface {
        Column(
            Modifier.padding(vertical = 16.dp)
        ) {
            CommonInputField(
                label = "Engineer name",
                placeholder = "Carlos Bedoy",
                currentValue = "",
                inputType = InputType.Decimal
            ) {}
            CommonInputField(
                label = "Engineer name",
                placeholder = "Carlos Bedoy",
                currentValue = "",
                inputType = InputType.Decimal,
                leadingIconId = R.drawable.baseline_battery_charging_full_24,
                enabled = true
            ) {}
            CommonInputField(
                label = "Engineer name",
                placeholder = "Carlos Bedoy",
                currentValue = "",
                inputType = InputType.Decimal,
                enabled = false
            ) {}
            CommonInputField(
                label = "Engineer email",
                placeholder = "Carlos Bedoy",
                currentValue = "",
                inputType = InputType.Email
            ) {}
            CommonInputField(
                label = "Engineer number",
                placeholder = "Carlos Bedoy",
                currentValue = "",
                inputType = InputType.Number
            ) {}
            CommonInputField(
                label = "Engineer bio",
                placeholder = "Carlos Bedoy",
                currentValue = "",
                inputType = InputType.Text
            ) {}
            CommonInputField(
                label = "Engineer age",
                placeholder = "Carlos Bedoy",
                currentValue = "",
                inputType = InputType.NumberPicker(IntRange(0, 30))
            ) {}
        }
    }
}