package com.cb.meapps.presentation.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cb.meapps.domain.fake.getFakeCards
import com.cb.meapps.domain.model.Card
import com.cb.meapps.presentation.ui.common.preview.SupportedDevicesPreview

@Composable
fun CardsSection(cards: List<Card>) {
    Column {
        val title = if (cards.isEmpty()) {
            "No Cards Yet? Let's Fix That!"
        } else {
            "Your Cards Are Ready to Roll!"
        }

        val subtitle = if (cards.isEmpty()) {
            "Add your first card, fill in the key details, and watch the magic happen! Say goodbye to forgotten due dates and hello to smooth sailing! \uD83D\uDCB3✨"
        } else {
            "You're all set! Your cards are organized and under control. Want to add another one? Keep everything in check and never miss a payment! \uD83D\uDCB3✅"
        }

        Column (
            Modifier.padding(vertical = 16.dp),
            Arrangement.spacedBy(16.dp)
        ) {
            BodyMediumSemiBold(
                text = title,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            BodyMediumLightPrimary(
                text = subtitle,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            if (cards.isEmpty()) {
                BodyMedium(
                    text = "No cards...",
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                )
            } else {
                Column {
                    cards.forEach {
                        CardItem(it)
                    }
                }
            }
        }
    }
}

@Composable
private fun CardItem(card: Card) {
    CommonCardItem(
        header = card.name,
        title = "Due date ${card.dueDate}",
        subtitle = "Cut off date ${card.cutOffDate}"
    )
}

@SupportedDevicesPreview
@Composable
private fun PreviewCardsSection() {
    Surface {
        Column {
            CardsSection(cards = emptyList())
            CardsSection(cards = getFakeCards().take(1))
            CardsSection(cards = getFakeCards())
        }
    }
}