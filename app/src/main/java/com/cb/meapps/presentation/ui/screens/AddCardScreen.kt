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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cb.meapps.R
import com.cb.meapps.presentation.ui.common.CommonInputField
import com.cb.meapps.presentation.ui.common.DayMateScaffold
import com.cb.meapps.presentation.ui.common.Header
import com.cb.meapps.presentation.ui.common.InputType
import com.cb.meapps.presentation.viewmodel.settings.SettingsAction
import kotlinx.coroutines.launch

@Composable
fun AddCardScreen(
    onAction: (SettingsAction) -> Unit,
    onCommit: () -> Unit,
    onCancel: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    DayMateScaffold(
        title = stringResource(R.string.add_new_card_title),
        snackbarHostState
    ) { paddingValues ->
        Column(
            Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Header(text = stringResource(R.string.add_new_card_header))
            CommonInputField(
                label = stringResource(R.string.add_new_card_card_name),
                placeholder = stringResource(R.string.add_new_card_card_placeholder),
                currentValue = "",
                inputType = InputType.Text,
                onValueChange = {}
            )
            CommonInputField(
                label = "Cut of date",
                placeholder = "Cut of date",
                currentValue = "",
                inputType = InputType.NumberPicker(IntRange(1, 30)),
                onValueChange = {}
            )
            CommonInputField(
                label = "Payment due date",
                placeholder = "Payment due date",
                currentValue = "",
                inputType = InputType.NumberPicker(IntRange(1, 31)),
                onValueChange = {}
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(
                onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar("No implemented")
                    }
                },
                enabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(text = stringResource(R.string.common_save))
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