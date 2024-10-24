package com.cb.meapps.presentation.ui.screens.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.cb.meapps.presentation.ui.common.BodyMediumSemiBold
import com.cb.meapps.presentation.ui.common.SmallPrimaryButton
import com.cb.meapps.presentation.ui.common.SmallSecondaryButton
import com.cb.meapps.presentation.ui.common.ValuePicker
import com.cb.meapps.presentation.ui.common.preview.SupportedDevicesPreview

@Composable
fun ValuePickerDialog(
    initialValue: Int,
    values: List<String>,
    onDismiss: () -> Unit,
    onValueSelected: (Int) -> Unit
) {
    var selectedValue by remember { mutableIntStateOf(initialValue) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { BodyMediumSemiBold(text = "Pick a value") },
        text = {
            Column(
                Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                Alignment.CenterHorizontally
            ) {
                ValuePicker(
                    value = selectedValue,
                    values = values,
                    onChanged = { newValue ->
                        selectedValue = newValue
                    }
                )
            }
        },
        confirmButton = {
            SmallPrimaryButton(text = "Select") {
                onValueSelected(selectedValue)
            }
        },
        dismissButton = {
            SmallSecondaryButton(text = "Cancel") {
                onDismiss()
            }
        }
    )
}

@SupportedDevicesPreview
@Composable
private fun PreviewValuePickerDialog() {
    ValuePickerDialog(
        initialValue = 0,
        values = listOf("Code", "Music", "Travel", "Drones"),
        onDismiss = {},
        onValueSelected = {}
    )
}