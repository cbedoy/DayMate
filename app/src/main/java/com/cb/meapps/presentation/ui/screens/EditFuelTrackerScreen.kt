package com.cb.meapps.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cb.meapps.R
import com.cb.meapps.domain.asMoney
import com.cb.meapps.domain.fake.getFakeTracks
import com.cb.meapps.domain.toDoubleOrZero
import com.cb.meapps.domain.toFloatOrZero
import com.cb.meapps.presentation.ui.common.CommonInputField
import com.cb.meapps.presentation.ui.common.DayMateScaffold
import com.cb.meapps.presentation.ui.common.InputType
import com.cb.meapps.presentation.viewmodel.FuelTrackerAction
import com.cb.meapps.presentation.viewmodel.FuelTrackerState

@Composable
fun EditFuelTrackerScreen(
    state: FuelTrackerState,
    onAction: (FuelTrackerAction) -> Unit
) {
    state.currentFuelTracker?: return

    var totalKms by remember {
        mutableStateOf(state.currentFuelTracker.totalKM.toString())
    }

    DayMateScaffold(
        title = "Adventure Miles!",
        onCreditClicked = {},
    ) { paddingValues ->
        Column(
            Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            CommonInputField(
                label = "Total kilometers you have drove",
                placeholder = "$100,000",
                currentValue = totalKms,
                inputType = InputType.Decimal,
                leadingIconId = R.drawable.baseline_directions_car_24,
                onValueChange = {
                    totalKms = it
                }
            )
            CommonInputField(
                label = "Total price you paid",
                placeholder = "$100,000",
                currentValue = state.currentFuelTracker.totalPrice.asMoney(),
                inputType = InputType.Decimal,
                leadingIconId = R.drawable.baseline_currency_exchange_24,
                enabled = false,
                onValueChange = {}
            )
            CommonInputField(
                label = "Total liters you have fill out",
                placeholder = "$100,000",
                currentValue = state.currentFuelTracker.liters.toString(),
                inputType = InputType.Decimal,
                leadingIconId = R.drawable.baseline_local_gas_station_24,
                enabled = false,
                onValueChange = {}
            )
            Text(
                "Here's the average of all your road adventures! Keep in mind it's just a reference, so use it to get a general idea of your fuel consumption. Keep exploring and your tank full! ⛽\uD83D\uDE09",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(16.dp)
            )
            OutlinedButton({
                onAction(FuelTrackerAction.OnCommitChanges(
                    kilometers = totalKms.toFloatOrZero(),
                    totalPrice = state.currentFuelTracker.totalPrice,
                    liters = state.currentFuelTracker.liters
                ))
            }, Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)) {
                Text(text = "Save")
            }
        }
    }
}

@Preview
@Composable
private fun PreviewEditFuelTrackerScreen() {
    EditFuelTrackerScreen(
        FuelTrackerState(
            currentFuelTracker = getFakeTracks().last()
        )
    ) {}
}