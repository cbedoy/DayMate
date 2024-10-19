package com.cb.meapps.presentation.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StickyHeaderView(
    titles: List<String> = emptyList()
) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .padding(16.dp)
    ) {
        titles.map {
            StickyHeaderViewText(it, getFontSize(titles))
        }
    }
}

private fun getFontSize(titles: List<String>) = when {
    titles.size == 1 -> 16.sp
    titles.size == 2 -> 14.sp
    titles.size <= 4 -> 12.sp
    else -> 10.sp
}

@Composable
private fun RowScope.StickyHeaderViewText(text: String, textSize: TextUnit) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.tertiary,
        fontWeight = FontWeight.Normal,
        fontSize = textSize,
        maxLines = 1,
        modifier = Modifier.weight(1.0f).background(MaterialTheme.colorScheme.tertiaryContainer)
    )
}

@Preview
@Composable
private fun PreviewStickyHeaderView() {
    Surface {
        Column(
            Modifier.fillMaxWidth(),
            Arrangement.spacedBy(16.dp)
        ){
            StickyHeaderView()
            StickyHeaderView(listOf("Header"))
            StickyHeaderView(listOf("Code", "Music"))
            StickyHeaderView(listOf("Code", "Music", "Travel", "Composing"))
            StickyHeaderView(
                listOf("Current", "Payment", "Daily", "Daily", "Accumulated", "Total")
            )
            StickyHeaderView(
                listOf("Date", "Current", "Payment", "Daily", "Accumulated", "Total", "Cards")
            )
        }
    }
}