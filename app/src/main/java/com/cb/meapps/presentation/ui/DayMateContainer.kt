package com.cb.meapps.presentation.ui

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cb.meapps.presentation.ui.screens.DocsScreen
import com.cb.meapps.presentation.ui.screens.LandingScreen
import com.cb.meapps.presentation.ui.screens.SettingsScreen
import com.cb.meapps.presentation.ui.screens.FinancialProjectionScreen
import com.cb.meapps.presentation.ui.screens.FinancialProjectionState
import com.cb.meapps.presentation.ui.screens.OnboardingScreen
import com.cb.meapps.presentation.viewmodel.DocsState
import com.cb.meapps.presentation.viewmodel.settings.SettingsAction
import com.cb.meapps.presentation.viewmodel.settings.SettingsState

@Composable
fun DayMateContainer(
    settingsState: SettingsState,
    docsState: DocsState,
    onCreditClicked : () -> Unit,
    onSettingsAction: (SettingsAction) -> Unit
) {

    val navController = rememberNavController()

    val onFinancialProjectionClick = {
        navController.navigate(DayMateRoutes.FinancialProjection.route)
    }
    val onSettingsClicked = {
        navController.navigate(DayMateRoutes.Settings.route)
    }
    val onDocsClicked = {
        navController.navigate(DayMateRoutes.Docs.route)
    }
    val onOnboardingCompleted = {
        onSettingsAction(SettingsAction.SkipOnboarding)

        navController.navigate(DayMateRoutes.Landing.route)
    }

    val initialRoute = if (settingsState.skipOnboarding) {
        DayMateRoutes.Landing.route
    } else {
        DayMateRoutes.Onboarding.route
    }

    NavHost(navController, startDestination = initialRoute, Modifier) {
        composable(DayMateRoutes.Landing.route) {
            LandingScreen(
                onFinancialProjectionClick = onFinancialProjectionClick,
                onSettingsClicked = onSettingsClicked,
                onCreditClicked = onCreditClicked,
                onDocsClicked = onDocsClicked,
                onOnboardingClicked = onOnboardingCompleted
            )
        }
        composable(DayMateRoutes.Onboarding.route) {
            OnboardingScreen(
                onCompleted = onOnboardingCompleted
            )
        }
        composable(DayMateRoutes.Docs.route) {
            DocsScreen(
                state = docsState
            )
        }
        composable(DayMateRoutes.FinancialProjection.route) {
            FinancialProjectionScreen(
                state = FinancialProjectionState(
                    initialSavings = settingsState.initialSavings.toDoubleOrZero(),
                    annualInterestRate = settingsState.annualInterestRate.toDoubleOrZero(),
                    biweeklyPayment = settingsState.biweeklyPayment.toDoubleOrZero(),
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
    Onboarding("onboarding"),
    Landing("landing"),
    Docs("docs"),
    FinancialProjection("financial_projection"),
    Settings("settings")
}