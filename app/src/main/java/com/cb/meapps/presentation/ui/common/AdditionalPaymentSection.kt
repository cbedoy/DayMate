package com.cb.meapps.presentation.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cb.meapps.data.model.AdditionalPayment
import com.cb.meapps.domain.asMoney
import com.cb.meapps.domain.fake.getFakeAdditionalPayments
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun AdditionalPaymentSection(
    additionalPayments: List<AdditionalPayment>,
    onDelete: (AdditionalPayment) -> Unit = {}
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        Arrangement.spacedBy(8.dp)
    ) {
        Text(
            "Additional payments to be tracked on",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        if (additionalPayments.isEmpty()) {
            Text(
                "No tracks...",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        } else {
            additionalPayments.map { additionalPayment ->
                SwipeBox(
                    modifier = Modifier,
                    onDelete = { onDelete(additionalPayment) },
                ) {
                    AdditionalPaymentItem(additionalPayment)
                }
            }
        }
    }
}

@Composable
fun AdditionalPaymentItem(additionalPayment: AdditionalPayment) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .border(1.dp, MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(16.dp))
            .background(
                MaterialTheme.colorScheme.onPrimary,
                RoundedCornerShape(16.dp)
            )
            .padding(16.dp),
        Arrangement.Start,
        Alignment.CenterVertically
    ) {
        Text(
            additionalPayment.value.asMoney(),
            modifier = Modifier.weight(1.0f),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 20.sp
        )
        Column(
            modifier = Modifier.weight(1.0f),
        ) {
            Text(
                additionalPayment.name.uppercase(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                getDayAndMonthFromDate(additionalPayment.day),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

fun getDayAndMonthFromDate(dayOfYear: Int, year: Int = Calendar.getInstance().get(Calendar.YEAR)): String {
    val calendar = Calendar.getInstance()

    calendar.set(Calendar.YEAR, year)
    calendar.set(Calendar.DAY_OF_YEAR, dayOfYear)

    val formatter = SimpleDateFormat("dd MMMM", Locale.getDefault())

    return formatter.format(calendar.time)
}

@Preview
@Composable
private fun PreviewAdditionalPaymentSection() {
    Surface {
        Column {
            AdditionalPaymentSection(
                additionalPayments = getFakeAdditionalPayments(),
                onDelete = {}
            )
            AdditionalPaymentSection(
                additionalPayments = listOf(),
                onDelete = {}
            )
        }
    }
}