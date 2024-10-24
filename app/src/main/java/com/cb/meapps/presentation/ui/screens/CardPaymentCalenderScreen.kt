@file:OptIn(ExperimentalFoundationApi::class)

package com.cb.meapps.presentation.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.ui.unit.dp
import com.cb.meapps.R
import com.cb.meapps.domain.fake.getFakeCards
import com.cb.meapps.domain.model.Card
import com.cb.meapps.presentation.ui.common.BodySmall
import com.cb.meapps.presentation.ui.common.DayMateScaffold
import com.cb.meapps.presentation.ui.common.StickyHeaderView
import com.cb.meapps.presentation.ui.common.preview.SupportedDevicesPreview
import com.cb.meapps.presentation.viewmodel.financial.ProjectionsAction
import com.cb.meapps.presentation.viewmodel.financial.ProjectionsState
import com.cb.meapps.presentation.viewmodel.settings.SettingsState

@Composable
fun CardPaymentCalendarScreen(
    settingsState: SettingsState,
    projectionsState: ProjectionsState,
    onProjectionsAction : (ProjectionsAction) -> Unit
) {
    rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    DayMateScaffold(
        title = stringResource(R.string.card_payment_calendar_title),
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
                    projectionsState.datesGroupedByMonth.forEach { (monthYear, datesInMonth) ->

                        stickyHeader(
                            key = monthYear
                        ) {
                            CardPaymentCalendarHeader(monthYear)
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
private fun CardPaymentCalendarHeader(monthYear: String) {
    StickyHeaderView(listOf(monthYear.uppercase()))
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
            BodySmall(text = stringResource(R.string.card_payment_calendar_no_payments_due))
        }
    }
}

@SupportedDevicesPreview
@Composable
private fun PreviewCardPaymentCalenderScreen() {
    CardPaymentCalendarScreen(
        settingsState = SettingsState(
            cards = getFakeCards()
        ),
        projectionsState = ProjectionsState(),
        onProjectionsAction = {}
    )
}
