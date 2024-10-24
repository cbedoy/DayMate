package com.cb.meapps.presentation.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cb.meapps.domain.asMoney
import com.cb.meapps.domain.fake.getFakeAdditionalPayments
import com.cb.meapps.domain.model.AdditionalPayment
import com.cb.meapps.presentation.ui.common.preview.SupportedDevicesPreview
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
        BodyMediumSemiBold(
            "Additional payments to be tracked on",
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        if (additionalPayments.isEmpty()) {
            EmptyData(
                "Oh no!",
                "No tracks to be shown",
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
    CommonCardItem(
        header = additionalPayment.value.asMoney(),
        title = additionalPayment.name,
        subtitle = getDayAndMonthFromDate(additionalPayment.day)
    )
}

fun getDayAndMonthFromDate(dayOfYear: Int, year: Int = Calendar.getInstance().get(Calendar.YEAR)): String {
    val calendar = Calendar.getInstance()

    calendar.set(Calendar.YEAR, year)
    calendar.set(Calendar.DAY_OF_YEAR, dayOfYear)

    val formatter = SimpleDateFormat("dd MMMM", Locale.getDefault())

    return formatter.format(calendar.time)
}

@SupportedDevicesPreview
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