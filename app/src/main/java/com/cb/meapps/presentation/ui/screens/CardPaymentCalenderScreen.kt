package com.cb.meapps.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cb.meapps.R
import com.cb.meapps.domain.fake.getFakeCards
import com.cb.meapps.domain.model.Card
import com.cb.meapps.presentation.ui.common.DayMateScaffold
import com.cb.meapps.presentation.viewmodel.settings.SettingsState
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun CardPaymentCalendarScreen(
    settingsState: SettingsState,
    onCreditClicked: () -> Unit
) {
    val generateNextDays = generateNextDays(360)
    val datesGroupedByMonth = generateNextDays.groupBy { it.monthName + " " + it.year }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    DayMateScaffold(
        title = stringResource(R.string.card_payment_calendar_title),
        onCreditClicked = onCreditClicked,
        snackbarHostState
    ) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.onTertiary)
        ) {
            Column(
                Modifier.fillMaxWidth()
            ) {
                // Use LazyColumn to display the list
                LazyColumn {
                    datesGroupedByMonth.forEach { (monthYear, datesInMonth) ->

                        item {
                            MonthHeader(monthYear)
                        }

                        items(datesInMonth) { dateInfo ->
                            val cardsDueToday = settingsState.cards.filter { card ->
                                card.dueDate == dateInfo.day
                            }
                            PaymentItem(dateInfo.date, cardsDueToday)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MonthHeader(monthYear: String) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .padding(16.dp)
    ) {
        Text(
            text = monthYear.uppercase(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.tertiary,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun PaymentItem(date: String, cardsDueToday: List<Card>) {
    val background = if (cardsDueToday.isNotEmpty()) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onPrimary
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(background)
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Text(
            text = date,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1.0f),
            color = if (cardsDueToday.isNotEmpty()) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.primary
            }
        )

        if (cardsDueToday.isNotEmpty()) {
            val cardNames = cardsDueToday.joinToString(", ") { it.name }
            Text(
                text = buildAnnotatedString {
                    append(stringResource(R.string.common_pay))
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(cardNames)
                    }
                    append(stringResource(R.string.comon_pay_this_day))
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        } else {
            Text(text = stringResource(R.string.card_payment_calendar_no_payments_due), style = MaterialTheme.typography.bodySmall)
        }
    }
}

private fun generateNextDays(days: Int = 360): List<DateInfo> {
    val dateFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
    val monthFormat = SimpleDateFormat("MMMM", Locale.getDefault())
    val currentDate = Calendar.getInstance()
    val dates = mutableListOf<DateInfo>()

    for (i in 0 until days) {
        val dateString = dateFormat.format(currentDate.time).uppercase()
        val day = currentDate.get(Calendar.DAY_OF_MONTH)
        val month = currentDate.get(Calendar.MONTH) + 1 // Enero es 0, por eso sumamos 1
        val monthName = monthFormat.format(currentDate.time)
        val year = currentDate.get(Calendar.YEAR)

        dates.add(
            DateInfo(
                date = dateString,
                day = day,
                month = month,
                monthName = monthName,
                year = year
            )
        )
        currentDate.add(Calendar.DAY_OF_YEAR, 1)
    }

    return dates
}

private data class DateInfo(
    val date: String,
    val day: Int,
    val month: Int,
    val monthName: String,
    val year: Int
)

@Preview
@Composable
private fun PreviewCardPaymentCalenderScreen() {
    CardPaymentCalendarScreen(
        settingsState = SettingsState(
            cards = getFakeCards()
        ),
        onCreditClicked = {}
    )
}
