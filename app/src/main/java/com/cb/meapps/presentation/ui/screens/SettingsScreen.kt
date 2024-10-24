package com.cb.meapps.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cb.meapps.domain.fake.getFakeAdditionalPayments
import com.cb.meapps.presentation.ui.DayMateRoute
import com.cb.meapps.presentation.ui.common.AdditionalPaymentSection
import com.cb.meapps.presentation.ui.common.BodyMedium
import com.cb.meapps.presentation.ui.common.BodyMediumLightPrimary
import com.cb.meapps.presentation.ui.common.BodyMediumSemiBold
import com.cb.meapps.presentation.ui.common.CardsSection
import com.cb.meapps.presentation.ui.common.CommonInputField
import com.cb.meapps.presentation.ui.common.DayMateScaffold
import com.cb.meapps.presentation.ui.common.InputType
import com.cb.meapps.presentation.ui.common.TextButton
import com.cb.meapps.presentation.ui.common.preview.SupportedDevicesPreview
import com.cb.meapps.presentation.viewmodel.settings.SettingsAction
import com.cb.meapps.presentation.viewmodel.settings.SettingsState

@Composable
fun SettingsScreen(
    settingsState: SettingsState,
    onNavigationClicked: (DayMateRoute) -> Unit,
    onAction: (SettingsAction) -> Unit
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    DayMateScaffold(
        title = "Tune Your Finances",
        snackbarHostState = snackbarHostState
    ) { paddingValues ->
        LazyColumn(
            Modifier.padding(paddingValues),
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ){
            item {
                BodyMedium(
                    "Ready to take control? Adjust your initial savings, interest rate, and biweekly payments to see your financial future shape up! Every tweak you make here helps DayMate keep your plans on point.",
                    modifier = Modifier.padding(horizontal = 16.dp)
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
                CommonInputField(
                    label = "Projection days",
                    placeholder = "360 days",
                    currentValue = settingsState.projectionDays,
                    inputType = InputType.Number,
                    onValueChange = {
                        onAction(SettingsAction.ChangeProjectionDays(it))
                    }
                )
            }
            item {
                AdditionalPaymentSection(
                    settingsState.additionalPayments,
                    onDelete = {
                        onAction(SettingsAction.DeleteAdditionalPayment(it.name))
                    }
                )
            }
            item {
                CardsSection(settingsState.cards)
            }
            item {
                SettingsSwitchHeadline(false)
            }
            item {
                SettingsEntryPoints(onNavigationClicked)
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
            BodyMediumLightPrimary(
                text = "Turn on reminders to get notified before your due date and keep those pesky late fees away. With automatic alerts, you'll always be on top of your payments, and you'll even see a countdown so you know exactly how many days you have left. Stay worry-free and let us do the remembering for you! \uD83D\uDCC5\uD83D\uDD14",
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
private fun SettingsEntryPoints(onNavigationClicked: (DayMateRoute) -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        Arrangement.spacedBy(8.dp)
    ){
        BodyMediumSemiBold(
            text = "Open your links bellow!"
        )
        TextButton( "Open Saving Journey") {
            onNavigationClicked(DayMateRoute.FinancialProjection)
        }
        TextButton( "Open Pay Day Calendar") {
            onNavigationClicked(DayMateRoute.CardPaymentCalendar)
        }
        TextButton( "Open Money Map") {
            onNavigationClicked(DayMateRoute.MoneyMap)
        }
        TextButton( "Add additional Payment") {
            onNavigationClicked(DayMateRoute.AdditionalPayments)
        }
        TextButton( "Add card") {
            onNavigationClicked(DayMateRoute.AddNewCard)
        }
    }
}

@SupportedDevicesPreview
@Composable
private fun PreviewSettingsEntryPoints() {
    Surface {
        SettingsEntryPoints {}
    }
}

@SupportedDevicesPreview
@Composable
private fun PreviewSettingsScreen() {
    SettingsScreen(
        settingsState = SettingsState(
            additionalPayments = getFakeAdditionalPayments()
        ),
        onAction = {},
        onNavigationClicked = {}
    )
}