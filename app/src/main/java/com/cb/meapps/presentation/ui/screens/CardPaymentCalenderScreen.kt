package com.cb.meapps.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cb.meapps.domain.model.Card
import com.cb.meapps.presentation.ui.common.CommonTopAppBar
import com.cb.meapps.presentation.ui.common.Credit
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

    Scaffold(
        topBar = {
            CommonTopAppBar(title = "Your Future in Numbers")
        },
        bottomBar = {
            Credit(onCreditClicked)
        }
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
                    items(generateNextDays) { date ->
                        val cardsDueToday = settingsState.cards.filter {
                            card -> card.dueDate == date.split(" ").first().toIntOrNull()
                        }
                        PaymentItem(date, cardsDueToday)
                    }
                }
            }
        }
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
                    append("Pay ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(cardNames)
                    }
                    append(" this day!")
                },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
        } else {
            Text(text = "No payments due today.", style = MaterialTheme.typography.bodySmall)
        }
    }
}

fun generateNextDays(days: Int = 360): List<String> {
    val dateFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
    val currentDate = Calendar.getInstance()
    val dates = mutableListOf<String>()

    for (i in 0 until days) {
        dates.add(dateFormat.format(currentDate.time).uppercase())
        currentDate.add(Calendar.DAY_OF_YEAR, 1)
    }

    return dates
}

@Preview
@Composable
private fun PreviewCardPaymentCalenderScreen() {
    CardPaymentCalendarScreen(
        settingsState = SettingsState(
            cards = listOf(
                Card(
                    name = "VISA Platinum",
                    cutOffDate = 7,
                    dueDate = 25
                ),
                Card(
                    name = "MasterCard Gold",
                    cutOffDate = 13,
                    dueDate = 30
                ),
                Card(
                    name = "American Express",
                    cutOffDate = 18,
                    dueDate = 11
                ),
                Card(
                    name = "Discover",
                    cutOffDate = 21,
                    dueDate = 25
                )
            )
        ),
        onCreditClicked = {}
    )
}