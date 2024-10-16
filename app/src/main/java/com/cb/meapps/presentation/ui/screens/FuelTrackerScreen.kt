package com.cb.meapps.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cb.meapps.R
import com.cb.meapps.domain.asMoney
import com.cb.meapps.domain.fake.getFakeTracks
import com.cb.meapps.domain.model.FuelTracker
import com.cb.meapps.domain.model.SummaryFullTracker
import com.cb.meapps.domain.round
import com.cb.meapps.presentation.ui.common.DayMateScaffold
import com.cb.meapps.presentation.ui.common.Header
import com.cb.meapps.presentation.viewmodel.FuelTrackerState
import kotlinx.coroutines.launch

@Composable
fun FuelTrackerScreen(
    state: FuelTrackerState,
    onCreditClicked: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    DayMateScaffold(
        title = "Adventure Miles!",
        onCreditClicked = onCreditClicked,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar("No implemented")
                    }
                },
                content = {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            )
        },
        snackbarHostState = snackbarHostState
    ) { paddingValues ->
        LazyColumn(
            Modifier.padding(paddingValues),
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                Header(text = "Track your journey, fuel costs, and discover how far your engine can go.")
            }
            item {
                state.summaryFullTracker?.let {
                    FuelTrackerSummary(it)
                }
            }
            item {
                Text(
                    text = "Tracks",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
            items(state.tracks) {
                FuelTrackerView(it)
            }
        }
    }
}

@Composable
private fun FuelTrackerSummary(summaryFullTracker: SummaryFullTracker) {
    Column {
        FuelTrackerViewContainer(
            totalKm = summaryFullTracker.totalKM,
            totalPrice = summaryFullTracker.totalPrice,
            liters = summaryFullTracker.totalLiters,
            kmPerLiter = summaryFullTracker.totalKmPerLiter
        )
        Text(
            "Here's the average of all your road adventures! Keep in mind it's just a reference, so use it to get a general idea of your fuel consumption. Keep exploring and your tank full! ⛽\uD83D\uDE09",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Light,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
private fun FuelTrackerView(fuelTracker: FuelTracker) {
    FuelTrackerViewContainer(
        totalKm = fuelTracker.totalKM,
        totalPrice = fuelTracker.totalPrice,
        liters = fuelTracker.liters,
        kmPerLiter = fuelTracker.kmPerLiter
    )
}

@Composable
private fun FuelTrackerViewContainer(
    totalKm: Float,
    totalPrice: Float,
    liters: Float,
    kmPerLiter: Float
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .border(1.dp, MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(16.dp))
            .background(
                MaterialTheme.colorScheme.onPrimary,
                RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        Arrangement.Center,
        Alignment.Start
    ) {
        Row {
            FuelTrackerText(
                text = "$totalKm KM",
                R.drawable.baseline_directions_car_24
            )
            FuelTrackerText(
                text = totalPrice.asMoney(),
                R.drawable.baseline_currency_exchange_24
            )
        }
        Row {
            FuelTrackerText(
                text = "$liters Liters",
                R.drawable.baseline_local_gas_station_24
            )
            FuelTrackerText(
                text = "${kmPerLiter.round(2)} km/l",
                R.drawable.baseline_battery_charging_full_24
            )
        }
    }
}

@Composable
private fun RowScope.FuelTrackerText(text: String, icon: Int) {
    val modifier = Modifier
        .fillMaxWidth()
        .weight(1.0f)

    Column(
        modifier.padding(vertical = 8.dp),
        Arrangement.spacedBy(16.dp),
        Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.inversePrimary
        )
        Text(
            text,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun PreviewFuelTrackerScreen() {

    val fakeTracks = getFakeTracks()
    val summaryFullTracker = SummaryFullTracker(
        totalKM = fakeTracks.map { it.totalKM }.sum(),
        totalLiters = fakeTracks.map { it.liters }.sum(),
        totalPrice = fakeTracks.map { it.totalPrice }.sum()
    )

    Surface {
        FuelTrackerScreen(
            state = FuelTrackerState(
                tracks = fakeTracks,
                summaryFullTracker = summaryFullTracker
            ),
            onCreditClicked = {}
        )
    }
}