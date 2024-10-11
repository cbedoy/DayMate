package com.cb.meapps.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cb.meapps.presentation.ui.screens.LandingScreen
import com.cb.meapps.presentation.ui.screens.SettingsScreen
import com.cb.meapps.presentation.ui.screens.FinancialProjectionScreen
import com.cb.meapps.presentation.ui.screens.FinancialProjectionState
import com.cb.meapps.presentation.viewmodel.settings.SettingsAction
import com.cb.meapps.presentation.viewmodel.settings.SettingsState

@Composable
fun DayMateContainer(
    settingsState: SettingsState,
    onSettingsAction: (SettingsAction) -> Unit
) {

    val navController = rememberNavController()

    val onFinancialProjectionClick = {
        navController.navigate(DayMateRoutes.FinancialProjection.route)
    }
    val onSettingsClicked = {
        navController.navigate(DayMateRoutes.Settings)
    }
    val onCreditClicked = {

    }

    NavHost(navController, startDestination = DayMateRoutes.Landing.route, Modifier) {
        composable(DayMateRoutes.Landing.route) {
            LandingScreen(
                onFinancialProjectionClick = onFinancialProjectionClick,
                onSettingsClicked = onSettingsClicked,
                onCreditClicked = onCreditClicked
            )
        }
        composable(DayMateRoutes.FinancialProjection.route) {
            FinancialProjectionScreen(
                state = FinancialProjectionState(
                    initialSavings = settingsState.initialSavings.toDoubleOrZero(),
                    annualInterestRate = settingsState.initialSavings.toDoubleOrZero(),
                    biweeklyPayment = settingsState.initialSavings.toDoubleOrZero(),
                ),
                onCreditClicked = onCreditClicked
            )
        }
        composable(DayMateRoutes.Settings.route) {
            SettingsScreen(
                settingsState,
                onAction = onSettingsAction,
                onCreditClicked = onCreditClicked
            )
        }
    }
}

private fun String.toDoubleOrZero(): Double {
    return this.toDoubleOrNull() ?: 0.0
}
enum class DayMateRoutes(val route: String) {
    Landing("landing"),
    FinancialProjection("financial_projection"),
    Settings("settings")
}