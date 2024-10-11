package com.cb.meapps

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun FinancialProjection(
    initialSavings: Double,
    annualInterestRate: Double,
    biweeklyPayment: Double,
    days: Int = 360
) {
    val calendar = Calendar.getInstance()
    var totalSavings = initialSavings
    var accumulatedInterest = 0.0

    // Date formatter for "1 SEPT" format
    val dateFormat = SimpleDateFormat("d MMM", Locale("es", "ES"))

    // Convert annual interest rate from percentage to decimal
    val annualInterestDecimal = annualInterestRate / 100.0
    // Calculate daily interest rate
    val dailyInterestRate = annualInterestDecimal / 360.0

    // Generate a list of ProjectionDay for the specified number of days
    val projectionDays = (0..days).map { dayIndex ->

        val current = totalSavings

        val newCal = calendar.clone() as Calendar
        newCal.add(Calendar.DAY_OF_YEAR, dayIndex)

        val dayOfMonth = newCal.get(Calendar.DAY_OF_MONTH)
        var paymentToday = 0.0

        val month = calendar.get(Calendar.MONTH) + 1

        val isLastDayOfFebruary = if (month == Calendar.FEBRUARY) {
            val lastDayOfFebruary = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            dayOfMonth == lastDayOfFebruary
        } else {
            false
        }

        // Check if today is the 15th or 30th or if today is the last day of feb
        val isPaymentDay = (dayOfMonth == 15 || dayOfMonth == 30 || isLastDayOfFebruary)

        // Payment is made only if it's the 15th or 30th, regardless of weekends
        if (isPaymentDay) {
            paymentToday = biweeklyPayment
        }

        val dailyInterest = totalSavings * dailyInterestRate
        accumulatedInterest += dailyInterest
        totalSavings += paymentToday + dailyInterest

        // Format the date using SimpleDateFormat
        val formattedDate = dateFormat.format(newCal.time).uppercase()

        // Create a ProjectionDay instance
        ProjectionDay(
            date = formattedDate,
            current = current.asMoney(),
            paymentToday = paymentToday.asMoney(),
            dailyInterest = dailyInterest.asMoney(),
            accumulatedInterest = accumulatedInterest.asMoney(),
            totalSavings = totalSavings.asMoney(),
            isPaymentDay = isPaymentDay
        )
    }

    Scaffold(
        bottomBar = {
            Credit()
        }
    ) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .background(colorScheme.onTertiary)
        ) {
            CurrentSavingState(initialSavings)
            ProjectionHeader()
            Column(
                Modifier.fillMaxWidth()
            ) {
                // Use LazyColumn to display the list
                LazyColumn {
                    items(projectionDays) { projectionDay ->
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
private fun CurrentSavingState(initialSavings: Double) {
    Text(
        text = "Current saving: ${initialSavings.asMoney()}".uppercase(),
        modifier = Modifier
            .fillMaxWidth()
            .background(colorScheme.onBackground)
            .padding(8.dp),
        color = colorScheme.onPrimary,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    )
}

@Composable
private fun Credit() {
    Column {
        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.Black.copy(alpha = 0.2f)))
        Text(
            text = "Made w/ ❤\uFE0F\uD83C\uDF2E by Carlos Bedoy",
            Modifier
                .background(colorScheme.onPrimary)
                .fillMaxWidth()
                .padding(16.dp),
            color = colorScheme.primary,
            textAlign = TextAlign.Center
        )
    }
}

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
            ProjectionRow(ProjectionDay(
                "1 Sept 2024",
                "100.00",
                "100.00",
                "200.00",
                "200.20",
                "20202.00",
                true
            ))
            ProjectionRow(ProjectionDay(
                "1 Sept 2024",
                "100.00",
                "100.00",
                "200.00",
                "200.20",
                "20202.00",
                false
            ))
        }
    }
}

@Preview
@Composable
fun PreviewFinancialProjection() {
    Surface {
        FinancialProjection(
            initialSavings = 100000.00,
            annualInterestRate = 12.5,
            biweeklyPayment = 37500.00
        )
    }
}

private fun Double.asMoney() = "\$${String.format("%,.0f", this)}"