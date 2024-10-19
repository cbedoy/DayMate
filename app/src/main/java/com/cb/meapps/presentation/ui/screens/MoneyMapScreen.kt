@file:OptIn(ExperimentalFoundationApi::class)

package com.cb.meapps.presentation.ui.screens


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cb.meapps.domain.fake.getFakeCombinedProjections
import com.cb.meapps.domain.model.CardPayment
import com.cb.meapps.domain.model.CombinedProjection
import com.cb.meapps.presentation.ui.common.DayMateScaffold
import com.cb.meapps.presentation.ui.common.StickyHeaderView
import com.cb.meapps.presentation.viewmodel.financial.ProjectionsState

@Composable
fun MoneyMapScreen(
    projectionsState: ProjectionsState
) {
    DayMateScaffold(title = "Money Map") { paddingValues ->
        Column {
            LazyColumn(
                Modifier.padding(paddingValues)
            ) {
                stickyHeader {
                    MoneyMapHeader()
                }
                items(projectionsState.combinedProjections) {
                    MoneyMapItem(it)
                }
            }
        }
    }
}

@Composable
private fun MoneyMapHeader() {
    StickyHeaderView(
        listOf("Date", "Current", "Payment", "Daily", "Accumulated", "Total", "Cards")
    )
}

@Composable
private fun MoneyMapItem(combinedProjection: CombinedProjection) {

    val isPaymentDay = combinedProjection.isPaymentDay
    val background = if (combinedProjection.isPaymentDay) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onPrimary
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(background)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MoneyMapText(text = combinedProjection.date, isPaymentDay)
        MoneyMapText(text = combinedProjection.current, isPaymentDay)
        MoneyMapText(text = combinedProjection.paymentToday, isPaymentDay)
        MoneyMapText(text = combinedProjection.dailyInterest, isPaymentDay)
        MoneyMapText(text = combinedProjection.accumulatedInterest, isPaymentDay)
        MoneyMapText(text = combinedProjection.totalSavings, isPaymentDay)
        MoneyMapCardNameText(cards = combinedProjection.cardPayments, isPaymentDay)
    }
}

@Composable
private fun RowScope.MoneyMapText(
    text: String,
    isPaymentDay: Boolean = false
) {
    Text(
        text = text,
        modifier = Modifier.weight(1.0f),
        fontSize = 10.sp,
        color = if (isPaymentDay) MaterialTheme.colorScheme.onPrimary else {
            MaterialTheme.colorScheme.primary
        },
        fontWeight = if (isPaymentDay) FontWeight.SemiBold else FontWeight.Light,
        maxLines = 1,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun RowScope.MoneyMapCardNameText(
    cards: List<CardPayment>,
    isPaymentDay: Boolean = false
) {

    val text = cards.joinToString(", ") { it.cardName }

    Text(
        text = text,
        modifier = Modifier.weight(1.0f),
        fontSize = 8.sp,
        color = if (isPaymentDay) MaterialTheme.colorScheme.onPrimary else {
            MaterialTheme.colorScheme.primary
        },
        fontWeight = if (isPaymentDay) FontWeight.Bold else FontWeight.Normal,
        textAlign = TextAlign.Center
    )
}

@Preview
@Composable
private fun PreviewMoneyMapItem() {

    val combinedProjections = getFakeCombinedProjections()

    Column {
        combinedProjections.map { combinedProjection ->
            MoneyMapItem(combinedProjection)
        }
    }
}


@Preview
@Composable
private fun PreviewMoneyMapScreen() {
    MoneyMapScreen(
        projectionsState = ProjectionsState()
    )
}