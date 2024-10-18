package com.cb.meapps.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cb.meapps.presentation.ui.DayMateRoute
import com.cb.meapps.presentation.ui.common.DayMateScaffold

@Composable
fun LandingScreen(
    onNavigationClicked: (DayMateRoute) -> Unit
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    DayMateScaffold(
        title = "Day Mate",
        snackbarHostState = snackbarHostState
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            LazyVerticalGrid(
                modifier = Modifier.padding(paddingValues = paddingValues),
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    LandingItem(
                        "Savings Journey",
                        "\uD83D\uDCCA"
                    ) { onNavigationClicked(DayMateRoute.FinancialProjection) }
                }
                item {
                    LandingItem(
                        "Pay Day Radar",
                        "\uD83D\uDCB3"
                    ) { onNavigationClicked(DayMateRoute.CardPaymentCalendar) }
                }
                item {
                    LandingItem(
                        "Money Map",
                        "\uD83E\uDDE0"
                    ) { onNavigationClicked(DayMateRoute.MoneyPlanner) }
                }
                item {
                    LandingItem(
                        "Fuel tracker",
                        "⛽"
                    ) { onNavigationClicked(DayMateRoute.FuelTracker) }
                }
                item {
                    LandingItem(
                        "My trips",
                        "\uD83D\uDE80"
                    ) { onNavigationClicked(DayMateRoute.TripPlanner) }
                }
                item {
                    LandingItem(
                        "Documents",
                        "\uD83D\uDCC4"
                    ) { onNavigationClicked(DayMateRoute.Docs) }
                }
                item {
                    LandingItem(
                        "Onboarding",
                        "\uD83D\uDCD9"
                    ) { onNavigationClicked(DayMateRoute.Onboarding) }
                }
                item {
                    LandingItem(
                        "Settings",
                        "⚙\uFE0F"
                    ) { onNavigationClicked(DayMateRoute.Settings) }
                }
            }
        }
    }
}

@Composable
private fun LandingItem(text: String, icon: String, onClick: () -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .border(1.dp, MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(16.dp))
            .background(
                MaterialTheme.colorScheme.onPrimary,
                RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .padding(vertical = 32.dp),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        Text(
            icon,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text.uppercase(),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }
}

@Preview
@Composable
private fun PreviewLandingScreen() {
    Surface {
        LandingScreen(
            onNavigationClicked = {}
        )
    }
}