package com.cb.meapps.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cb.meapps.R
import com.cb.meapps.domain.fake.getFakeCards
import com.cb.meapps.domain.model.Card
import com.cb.meapps.presentation.ui.common.CommonInputField
import com.cb.meapps.presentation.ui.common.DayMateScaffold
import com.cb.meapps.presentation.ui.common.Header
import com.cb.meapps.presentation.ui.common.InputType
import com.cb.meapps.presentation.viewmodel.settings.SettingsAction
import com.cb.meapps.presentation.viewmodel.settings.SettingsState

@Composable
fun SettingsScreen(
    settingsState: SettingsState,
    onFinancialProjectionClicked: () -> Unit,
    onAddNewCardClicked: () -> Unit,
    onAction: (SettingsAction) -> Unit,
    onCreditClicked: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    DayMateScaffold(
        title = "Tune Your Finances",
        onCreditClicked = onCreditClicked,
        snackbarHostState = snackbarHostState
    ) { paddingValues ->
        LazyColumn(
            Modifier.padding(paddingValues),
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ){
            item {
                Header(
                    "Ready to take control? Adjust your initial savings, interest rate, and biweekly payments to see your financial future shape up! Every tweak you make here helps DayMate keep your plans on point.",
                )
            }
            item {
                CommonInputField(
                    label = "Initial savings",
                    placeholder = "$100,000",
                    currentValue = settingsState.initialSavings,
                    inputType = InputType.Number,
                    onValueChange = {
                        onAction(SettingsAction.ChangeInitialSavings(it))
                    }
                )
            }
            item {
                CommonInputField(
                    label = "Annual interest rate",
                    placeholder = "15.45%",
                    currentValue = settingsState.annualInterestRate,
                    inputType = InputType.Decimal,
                    onValueChange = {
                        onAction(SettingsAction.ChangeAnnualInterestRate(it))
                    }
                )
            }
            item {
                CommonInputField(
                    label = "Biweekly payment",
                    placeholder = "$12345",
                    currentValue = settingsState.biweeklyPayment,
                    inputType = InputType.Number,
                    onValueChange = {
                        onAction(SettingsAction.ChangeBiweeklyPayment(it))
                    }
                )
            }
            item {
                OutlinedButton(onFinancialProjectionClicked,
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)) {
                    Text(text = "Open Financial projection")
                }
            }
            item {
                SettingsCardHeadline(settingsState.cards, onAddNewCardClicked)
            }

            item {
                SettingsSwitchHeadline(false)
            }

        }
    }
}

@Composable
private fun SettingsSwitchHeadline(checked: Boolean) {
    var isChecked by remember { mutableStateOf(checked) }

    Column (
        Modifier.padding(vertical = 16.dp),
        Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Never Miss a Payment Again!",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Turn on reminders to get notified before your due date and keep those pesky late fees away. With automatic alerts, you'll always be on top of your payments, and you'll even see a countdown so you know exactly how many days you have left. Stay worry-free and let us do the remembering for you! \uD83D\uDCC5\uD83D\uDD14",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(1.0f)
            )
            Switch(
                checked = isChecked,
                onCheckedChange = { isChecked = it },
            )
        }


    }
}

@Composable
private fun SettingsCardHeadline(cards: List<Card>, addNewCard: () -> Unit) {
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
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            if (cards.isEmpty()) {
                EmptyDataNoCards()
            } else {
                Column {
                    cards.forEach {
                        SettingsCardPreview(it)
                    }
                }
            }
            OutlinedButton(addNewCard,
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)) {
                Text(text = "Add card")
            }
        }
    }
}

@Composable
private fun EmptyDataNoCards() {
    Text(
        text = "No cards...",
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Light,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        textAlign = TextAlign.Center
    )
}

@Composable
private fun SettingsCardPreview(card: Card) {
    Row(
        Modifier.padding(horizontal = 16.dp),
        Arrangement.Center,
        Alignment.CenterVertically
    ) {
        Text(
            text = card.name,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .weight(1.0f)
        )
        Column {
            Text(
                text = "Due date ${card.dueDate}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Text(
                text = "Cut off date ${card.cutOffDate}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.baseline_navigate_next_24),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview
@Composable
private fun PreviewNoCardView() {
    Surface {
        Column {
            SettingsCardHeadline(cards = emptyList(), {})
            SettingsCardHeadline(cards = getFakeCards().take(1), {})
            SettingsCardHeadline(cards = getFakeCards()) {}
        }
    }
}

@Preview
@Composable
private fun PreviewSettingsScreen() {
    SettingsScreen(
        settingsState = SettingsState(),
        onFinancialProjectionClicked = {},
        onAction = {},
        onCreditClicked = {},
        onAddNewCardClicked = {}
    )
}