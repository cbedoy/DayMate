package com.cb.meapps.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cb.meapps.presentation.ui.common.CommonInputField
import com.cb.meapps.presentation.ui.common.CommonTopAppBar
import com.cb.meapps.presentation.ui.common.Header
import com.cb.meapps.presentation.ui.common.InputType
import com.cb.meapps.presentation.viewmodel.settings.SettingsAction

@Composable
fun AddCardScreen(
    onAction: (SettingsAction) -> Unit,
    onCommit: () -> Unit,
    onCancel: () -> Unit
) {
    Scaffold(
        topBar = {
            CommonTopAppBar(title = "Add Your Card to the Club!")
        }
    ) { paddingValues ->
        Column(
            Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Header(text = "Give it a name, jot down the key dates, and you're all set! Your card will be organized and under control. No more missed payment dates! \uD83D\uDCB3\uD83D\uDCC5")
            CommonInputField(
                label = "Card name",
                placeholder = "Hey banco",
                currentValue = "",
                inputType = InputType.TEXT,
                onValueChange = {}
            )
            CommonInputField(
                label = "Cut of date",
                placeholder = "Cut of date",
                currentValue = "",
                inputType = InputType.NumberPicker(IntRange(0, 30)),
                onValueChange = {}
            )
            CommonInputField(
                label = "Payment due date",
                placeholder = "Payment due date",
                currentValue = "",
                inputType = InputType.NumberPicker(IntRange(0, 31)),
                onValueChange = {}
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(
                onClick = {

                },
                enabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(text = "Save")
            }
        }
    }
}

@Preview
@Composable
private fun PreviewAddCardScreen() {
    AddCardScreen(
        onAction = {},
        onCommit = {},
        onCancel = {}
    )
}