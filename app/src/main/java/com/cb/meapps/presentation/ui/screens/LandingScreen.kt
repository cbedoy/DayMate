package com.cb.meapps.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cb.meapps.presentation.ui.common.CommonTopAppBar
import com.cb.meapps.presentation.ui.common.Credit

@Composable
fun LandingScreen(
    onFinancialProjectionClick: () -> Unit,
    onSettingsClicked: () -> Unit,
    onDocsClicked: () -> Unit,
    onCardPaymentCalendar: () -> Unit,
    onOnboardingClicked: () -> Unit,
    onCreditClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            CommonTopAppBar(title = "Day Mate")
        },
        bottomBar = {
            Credit(onCreditClicked)
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxHeight(), contentAlignment = Alignment.Center) {
            LazyColumn(Modifier.padding(paddingValues)) {
                item {
                    LandingItem(onDocsClicked, "Documents")
                }
                item {
                    LandingItem(onFinancialProjectionClick, "Financial Projection")
                }
                item {
                    LandingItem(onCardPaymentCalendar, "Card Payment Calendar")
                }
                item {
                    LandingItem(onOnboardingClicked, "Onboarding")
                }
                item {
                    LandingItem(onSettingsClicked, "Settings")
                }

            }
        }
    }
}

@Composable
private fun LandingItem(onClick: () -> Unit, text: String) {
    Button(onClick,
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)) {
        Text(text = text)
    }
}

@Preview
@Composable
private fun PreviewLandingScreen() {
    Surface {
        LandingScreen(
            onFinancialProjectionClick = {},
            onSettingsClicked = {},
            onCreditClicked = {},
            onDocsClicked = {},
            onOnboardingClicked = {}
        )
    }
}