package com.cb.meapps.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cb.meapps.R
import com.cb.meapps.presentation.ui.common.CommonTopAppBar
import com.cb.meapps.presentation.ui.common.Credit
import com.cb.meapps.presentation.viewmodel.financial.FinancialProjectionState
import com.cb.meapps.presentation.viewmodel.settings.SettingsState
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

typealias OnCalculateFinancialProjection = (initialSavings: Double, annualInterestRate: Double, biweeklyPayment: Double) -> Unit

@Composable
fun FinancialProjectionScreen(
    settingsState: SettingsState,
    financialProjectionState: FinancialProjectionState,
    onCalculateFinancialProjection: OnCalculateFinancialProjection,
    onCreditClicked: () -> Unit
) {
    LaunchedEffect(Unit){
        onCalculateFinancialProjection(
            settingsState.initialSavings.toDoubleOrZero(),
            settingsState.annualInterestRate.toDoubleOrZero(),
            settingsState.biweeklyPayment.toDoubleOrZero()
        )
    }

    Scaffold(
        topBar = {
            CommonTopAppBar(title = stringResource(R.string.financial_projection_title))
        },
        bottomBar = {
            Credit(onCreditClicked)
        }
    ) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
        ) {
            ProjectionHeader()
            Column(
                Modifier.fillMaxWidth()
            ) {
                // Use LazyColumn to display the list
                LazyColumn {
                    items(financialProjectionState.projectionDays) { projectionDay ->
                        ProjectionRow(projectionDay = projectionDay)
                    }
                }
            }
        }
    }
}

data class ProjectionDay(
    val date: String,
    val current: String,
    val paymentToday: String,
    val dailyInterest: String,
    val accumulatedInterest: String,
    val totalSavings: String,
    val isPaymentDay: Boolean
)

@Composable
private fun ProjectionHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorScheme.onBackground)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ProjectionHeaderText(text = "Date")
        ProjectionHeaderText(text = "Current")
        ProjectionHeaderText(text = "Payment")
        ProjectionHeaderText(text = "Daily")
        ProjectionHeaderText(text = "Accumulated")
        ProjectionHeaderText(text = "Total")
    }
}

@Composable
private fun RowScope.ProjectionHeaderText(text: String) = Text(
    text = text,
    Modifier.weight(1.0f),
    color = colorScheme.onPrimary,
    fontWeight = FontWeight.SemiBold,
    fontSize = 10.sp
)

@Composable
private fun ProjectionRow(projectionDay: ProjectionDay) {
    Column {
        ProjectionRowDivider(projectionDay)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(if (projectionDay.isPaymentDay) colorScheme.primary else colorScheme.onPrimary)
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ProjectionRowText(text = projectionDay.date, projectionDay.isPaymentDay)
            ProjectionRowText(text = projectionDay.current, projectionDay.isPaymentDay)
            ProjectionRowText(text = projectionDay.paymentToday, projectionDay.isPaymentDay)
            ProjectionRowText(text = projectionDay.dailyInterest, projectionDay.isPaymentDay)
            ProjectionRowText(text = projectionDay.accumulatedInterest, projectionDay.isPaymentDay)
            ProjectionRowText(text = projectionDay.totalSavings, projectionDay.isPaymentDay)
        }
        ProjectionRowDivider(projectionDay)
    }
}

@Composable
private fun RowScope.ProjectionRowText(text: String, isPaymentDay: Boolean) {
    Text(
        text = text,
        modifier = Modifier.weight(1.0f),
        fontSize = 10.sp,
        color = if (isPaymentDay) colorScheme.onPrimary else {
            colorScheme.primary
        },
        fontWeight = if (isPaymentDay) FontWeight.SemiBold else FontWeight.Light
    )
}

@Composable
private fun ProjectionRowDivider(projectionDay: ProjectionDay) {
    if (projectionDay.isPaymentDay) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(colorScheme.secondary.copy(alpha = 0.2f)))
    }
}

@Preview
@Composable
private fun PreviewProjectionRow() {
    Surface {
        Column {
            ProjectionRow(
                ProjectionDay(
                "1 Sept 2024",
                "100.00",
                "100.00",
                "200.00",
                "200.20",
                "20202.00",
                true
            )
            )
            ProjectionRow(
                ProjectionDay(
                "1 Sept 2024",
                "100.00",
                "100.00",
                "200.00",
                "200.20",
                "20202.00",
                false
            )
            )
        }
    }
}

@Preview
@Composable
fun PreviewFinancialProjection() {
    Surface {
        FinancialProjectionScreen(
            settingsState = SettingsState(),
            financialProjectionState = FinancialProjectionState(),
            onCalculateFinancialProjection = {_, _, _ -> },
            onCreditClicked = {}
        )
    }
}

private fun String.toDoubleOrZero(): Double {
    return this.toDoubleOrNull() ?: 0.0
}