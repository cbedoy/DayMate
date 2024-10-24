package com.cb.meapps.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cb.meapps.R
import com.cb.meapps.domain.toFloatOrZero
import com.cb.meapps.domain.toIntOrZero
import com.cb.meapps.presentation.ui.common.BodyMedium
import com.cb.meapps.presentation.ui.common.CommonInputField
import com.cb.meapps.presentation.ui.common.DayMateScaffold
import com.cb.meapps.presentation.ui.common.InputType
import com.cb.meapps.presentation.ui.common.preview.SupportedDevicesPreview
import com.cb.meapps.presentation.viewmodel.settings.SettingsAction
import com.cb.meapps.presentation.viewmodel.settings.SettingsState

@Composable
fun AddCardScreen(
    settingsState: SettingsState,
    onAction: (SettingsAction) -> Unit
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var cardName by remember { mutableStateOf("") }
    var cutOfDate by remember { mutableStateOf("") }
    var paymentCutOfDate by remember { mutableStateOf("") }
    var debt by remember { mutableStateOf("") }

    val saveButtonEnabled by remember {
        derivedStateOf {
            cardName.isNotEmpty() && cutOfDate.isNotEmpty() && paymentCutOfDate.isNotEmpty() && debt.isNotEmpty()
        }
    }

    DayMateScaffold(
        title = stringResource(R.string.add_new_card_title),
        snackbarHostState
    ) { paddingValues ->
        Column(
            Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            BodyMedium(
                text = stringResource(R.string.add_new_card_header),
                Modifier.padding(horizontal = 16.dp)
            )
            CommonInputField(
                label = stringResource(R.string.add_new_card_card_name),
                placeholder = stringResource(R.string.add_new_card_card_placeholder),
                currentValue = cardName,
                inputType = InputType.Text,
                onValueChange = {
                    cardName = it
                }
            )
            CommonInputField(
                label = "Cut of date",
                placeholder = "Cut of date",
                currentValue = cutOfDate,
                inputType =  InputType.NumberPicker(IntRange(1, 31)),
                onValueChange = {
                    cutOfDate = it
                }
            )
            CommonInputField(
                label = "Payment due date",
                placeholder = "Payment due date",
                currentValue = paymentCutOfDate,
                inputType = InputType.NumberPicker(IntRange(1, 31)),
                onValueChange = {
                    paymentCutOfDate = it
                }
            )
            CommonInputField(
                label = "Debt",
                placeholder = "Debt",
                currentValue = debt,
                inputType = InputType.Decimal,
                onValueChange = {
                    debt = it
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(
                onClick = {
                    onAction(SettingsAction.AddNewCard(
                        name = cardName,
                        cutOffDate = cutOfDate.toIntOrZero(),
                        dueDate = paymentCutOfDate.toIntOrZero(),
                        debt = debt.toFloatOrZero()
                    ))
                },
                enabled = saveButtonEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(text = stringResource(R.string.common_save))
            }
        }
    }
}

@SupportedDevicesPreview
@Composable
private fun PreviewAddCardScreen() {
    AddCardScreen(
        settingsState = SettingsState(),
        onAction = {}
    )
}