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
import com.cb.meapps.presentation.ui.common.NumberPicker

@Composable
fun NumberPickerDialog(
    initialValue: Int,
    onDismiss: () -> Unit,
    onValueSelected: (Int) -> Unit,
    range: IntRange
) {
    var selectedValue by remember { mutableIntStateOf(initialValue) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Pick a number") },
        text = {
            Column(
                Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                Alignment.CenterHorizontally
            ) {
                NumberPicker(
                    value = selectedValue,
                    range = range,
                    onChanged = { newValue ->
                        selectedValue = newValue
                    }
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