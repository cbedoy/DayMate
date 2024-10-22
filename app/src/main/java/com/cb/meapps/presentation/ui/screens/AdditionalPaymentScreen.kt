package com.cb.meapps.presentation.ui.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cb.meapps.domain.fake.getFakeAdditionalPayments
import com.cb.meapps.domain.toFloatOrZero
import com.cb.meapps.presentation.ui.common.AdditionalPaymentSection
import com.cb.meapps.presentation.ui.common.CommonInputField
import com.cb.meapps.presentation.ui.common.DayMateScaffold
import com.cb.meapps.presentation.ui.common.InputType
import com.cb.meapps.presentation.viewmodel.settings.SettingsAction
import com.cb.meapps.presentation.viewmodel.settings.SettingsState

@Composable
fun AdditionalPaymentScreen(
    settingsState: SettingsState,
    onAction: (SettingsAction) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var day by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    val confirmationButtonVisible by remember {
        derivedStateOf {
            name.isNotEmpty() && day.isNotEmpty() && amount.isNotEmpty()
        }
    }

    DayMateScaffold(
        title = "Additional Payments"
    ) { paddingValues ->
        Column(Modifier.padding(paddingValues), Arrangement.spacedBy(8.dp)) {
            CommonInputField(
                label = "Name",
                placeholder = "Annual Bonus",
                currentValue = name,
                inputType = InputType.Text,
                onValueChange = {
                    name = it
                }
            )
            CommonInputField(
                label = "Day",
                placeholder = "01 Sept",
                currentValue = day,
                inputType = InputType.ValuePicker(
                    selectedIndex = settingsState.days.safeIndexOf(day),
                    values = settingsState.days
                ),
                onValueChange = {
                    day = it
                }
            )
            CommonInputField(
                label = "Initial savings",
                placeholder = "$100,000",
                currentValue = amount,
                inputType = InputType.Decimal,
                onValueChange = {
                    amount = it
                }
            )
            OutlinedButton(
                onClick = {
                    onAction(
                        SettingsAction.SaveAdditionalPayment(
                            name = name,
                            day = settingsState.days.safeIndexOf(day),
                            value = amount.toFloatOrZero()
                        )
                    )
                },
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                enabled = confirmationButtonVisible
            ) {
                Text(text = "Save")
            }
            AdditionalPaymentSection(settingsState.additionalPayments) {
                onAction(SettingsAction.DeleteAdditionalPayment(it.name))
            }
        }
    }
}

private fun List<String>.safeIndexOf(value: String): Int {
    return if (!this.contains(value)) 0 else indexOf(value)
}

@Preview
@Composable
private fun PreviewAdditionalPaymentScreen() {
    AdditionalPaymentScreen(
        settingsState = SettingsState()
    ) {}
}

@Preview
@Composable
private fun PreviewAdditionalPaymentScreenWithData() {
    AdditionalPaymentScreen(
        settingsState = SettingsState(
            additionalPayments = getFakeAdditionalPayments(),

        )
    ) {}
}