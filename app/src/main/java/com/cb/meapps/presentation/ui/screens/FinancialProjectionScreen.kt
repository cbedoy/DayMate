@file:OptIn(ExperimentalFoundationApi::class)

package com.cb.meapps.presentation.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cb.meapps.R
import com.cb.meapps.domain.model.ProjectionDay
import com.cb.meapps.domain.toDoubleOrZero
import com.cb.meapps.presentation.ui.common.DayMateScaffold
import com.cb.meapps.presentation.viewmodel.financial.ProjectionsAction
import com.cb.meapps.presentation.viewmodel.financial.ProjectionsState
import com.cb.meapps.presentation.viewmodel.settings.SettingsState

@Composable
fun FinancialProjectionScreen(
    settingsState: SettingsState,
    projectionsState: ProjectionsState,
    onProjectionsAction : (ProjectionsAction) -> Unit
) {
    LaunchedEffect(Unit){
        onProjectionsAction(
            ProjectionsAction.CalculateFinancialProjection(
                settingsState.initialSavings.toDoubleOrZero(),
                settingsState.annualInterestRate.toDoubleOrZero(),
                settingsState.biweeklyPayment.toDoubleOrZero()
            )
        )
    }

    val snackbarHostState = remember { SnackbarHostState() }

    DayMateScaffold(
        title = stringResource(R.string.financial_projection_title),
        snackbarHostState = snackbarHostState
    ) { paddingValues ->
        LazyColumn(
            Modifier.padding(paddingValues)
        ) {

            projectionsState.projectionDays.groupBy {
                "${it.date.split(" ").last()} ${it.year}"
            }.forEach { (monthYear, projections) ->
                stickyHeader {
                    MonthHeader(monthYear)
                }
                items(projections) { projectionDay ->
                    ProjectionRow(projectionDay = projectionDay)
                }
            }
        }
    }
}

@Composable
private fun MonthHeader(monthYear: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(colorScheme.onBackground)
            .padding(16.dp)
    ) {
        ProjectionHeaderText(text = monthYear)
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
                    year = 2024,
                    date = "1 Sept 2024",
                    current = "100.00",
                    paymentToday = "100.00",
                    dailyInterest = "200.00",
                    accumulatedInterest = "200.20",
                    totalSavings = "20202.00",
                    isPaymentDay = true
                )
            )
            ProjectionRow(
                ProjectionDay(
                    year = 2024,
                    date = "1 Sept 2024",
                    current = "100.00",
                    paymentToday = "100.00",
                    dailyInterest = "200.00",
                    accumulatedInterest = "200.20",
                    totalSavings = "20202.00",
                    isPaymentDay = false
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
            projectionsState = ProjectionsState(),
            onProjectionsAction = {}
        )
    }
}