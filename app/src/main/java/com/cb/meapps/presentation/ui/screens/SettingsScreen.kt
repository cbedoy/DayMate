package com.cb.meapps.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cb.meapps.presentation.ui.common.Credit
import com.cb.meapps.presentation.viewmodel.settings.SettingsAction
import com.cb.meapps.presentation.viewmodel.settings.SettingsState

@Composable
fun SettingsScreen(
    settingsState: SettingsState,
    onAction: (SettingsAction) -> Unit,
    onCreditClicked: () -> Unit
) {
    Scaffold(
        bottomBar = {
            Credit(onCreditClicked)
        }
    ) { paddingValues ->
        LazyColumn(Modifier.padding(paddingValues)){
            item {
                NumberInputField(
                    label = "Initial savings",
                    currentValue = settingsState.initialSavings,
                    onValueChange = {
                        onAction(SettingsAction.ChangeInitialSavings(it))
                    }
                )
            }
            item {
                NumberInputField(
                    label = "Annual interest rate",
                    currentValue = settingsState.annualInterestRate,
                    onValueChange = {
                        onAction(SettingsAction.ChangeAnnualInterestRate(it))
                    }
                )
            }
            item {
                NumberInputField(
                    label = "Biweekly payment",
                    currentValue = settingsState.biweeklyPayment,
                    onValueChange = {
                        onAction(SettingsAction.ChangeBiweeklyPayment(it))
                    }
                )
            }
        }
    }
}

@Composable
fun NumberInputField(
    label: String,
    currentValue: String,
    onValueChange: (String) -> Unit
) {
    Column {
        OutlinedTextField(
            value = currentValue,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() }) {
                    onValueChange(newValue)
                }
            },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            label = { Text(label) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}

@Preview
@Composable
private fun PreviewSettingsScreen() {
    SettingsScreen(
        settingsState = SettingsState(),
        onAction = {},
        onCreditClicked = {}
    )
}