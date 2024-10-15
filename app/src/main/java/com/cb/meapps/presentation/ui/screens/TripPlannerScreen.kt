package com.cb.meapps.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cb.meapps.domain.asMoney
import com.cb.meapps.domain.model.Trip
import com.cb.meapps.domain.model.getFakeTrips
import com.cb.meapps.presentation.ui.common.CommonTopAppBar
import com.cb.meapps.presentation.ui.common.Credit

@Composable
fun TripPlannerScreen(
    state: TripPlannerState = TripPlannerState(getFakeTrips()),
    onCreditClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            CommonTopAppBar(title = "Lets trip!")
        },
        bottomBar = {
            Credit(onCreditClicked)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {  },
                content = {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        LazyColumn(
            Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(state.trips) { trip ->
                TripView(trip)
            }
        }
    }
}

@Composable
fun TripView(trip: Trip) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .heightIn(100.dp),
        Arrangement.spacedBy(16.dp),
        Alignment.CenterVertically
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .fillMaxHeight()
                .weight(1.0f)
                .background(
                    MaterialTheme.colorScheme.onPrimary,
                    RoundedCornerShape(16.dp)
                )
                .padding(16.dp),
            Arrangement.Center,
            Alignment.CenterHorizontally
        ) {
            Text(
                "Total".uppercase(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                trip.expenses.map { it.amount }.sum().asMoney(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Column(
            Modifier
                .fillMaxSize()
                .weight(3.0f)
                .background(
                    MaterialTheme.colorScheme.onPrimary,
                    RoundedCornerShape(16.dp)
                )
                .padding(16.dp),
            Arrangement.Center,
            Alignment.Start
        ) {
            Text(
                trip.name.uppercase(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                "Divided into ${trip.members.size} equals parts by ${trip.expenses.map { it.amount }.sum().div(trip.members.size).asMoney()} each",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.primary
            )
            OutlinedButton(onClick = { /*TODO*/ }) {
                Text(text = "See more")
            }
        }
    }
}

@Preview
@Composable
private fun PreviewTripPlannerScreen() {
    TripPlannerScreen {}
}

data class TripPlannerState(
    val trips: List<Trip> = getFakeTrips()
)