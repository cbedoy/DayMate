package com.cb.meapps.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cb.meapps.presentation.ui.screens.AddCardScreen
import com.cb.meapps.presentation.ui.screens.CardPaymentCalendarScreen
import com.cb.meapps.presentation.ui.screens.DocsScreen
import com.cb.meapps.presentation.ui.screens.FinancialProjectionScreen
import com.cb.meapps.presentation.ui.screens.FuelTrackerScreen
import com.cb.meapps.presentation.ui.screens.LandingScreen
import com.cb.meapps.presentation.ui.screens.OnCalculateFinancialProjection
import com.cb.meapps.presentation.ui.screens.OnboardingScreen
import com.cb.meapps.presentation.ui.screens.SettingsScreen
import com.cb.meapps.presentation.ui.screens.TripPlannerScreen
import com.cb.meapps.presentation.viewmodel.DocsState
import com.cb.meapps.presentation.viewmodel.FuelTrackerState
import com.cb.meapps.presentation.viewmodel.financial.FinancialProjectionState
import com.cb.meapps.presentation.viewmodel.settings.SettingsAction
import com.cb.meapps.presentation.viewmodel.settings.SettingsState

@Composable
fun DayMateContainer(
    settingsState: SettingsState,
    docsState: DocsState,
    financialProjectionState: FinancialProjectionState,
    fuelTrackerState: FuelTrackerState,
    onCalculateFinancialProjection: OnCalculateFinancialProjection,
    onCreditClicked : () -> Unit,
    onSettingsAction: (SettingsAction) -> Unit
) {

    val navController = rememberNavController()

    val onFinancialProjectionClick = remember(navController) {
        {
            navController.navigate(DayMateRoute.FinancialProjection.route) {

            }
        }
    }
    val onSettingsClicked = remember(navController) {
        {
            navController.navigate(DayMateRoute.Settings.route)
        }
    }
    val onDocsClicked = remember(navController) {
        {
            navController.navigate(DayMateRoute.Docs.route)
        }
    }
    val onOnboardingCompletedClicked = remember(navController) {
        {
            onSettingsAction(SettingsAction.SkipOnboarding)

            navController.navigate(DayMateRoute.Landing.route)
        }
    }

    val onAddNewCardClicked = remember(navController) {
        {
            navController.navigate(DayMateRoute.AddNewCard.route)
        }
    }
    val onCardPaymentCalendarClicked = remember(navController) {
        {
            navController.navigate(DayMateRoute.CardPaymentCalendar.route)
        }
    }

    val onMyTripsClicked = remember(navController) {
        {
            navController.navigate(DayMateRoute.TripPlanner.route)
        }
    }

    val onFuelTrackerClicked = remember(navController) {
        {
            navController.navigate(DayMateRoute.FuelTracker.route)
        }
    }

    // Navigate to the onboarding screen if the user hasn't skipped it
    val initialRoute = if (settingsState.skipOnboarding) {
        DayMateRoute.Landing.route
    } else {
        DayMateRoute.Onboarding.route
    }

    NavHost(navController, startDestination = initialRoute, Modifier) {
        composable(DayMateRoute.Landing.route) {
            LandingScreen(
                onFinancialProjectionClick = onFinancialProjectionClick,
                onSettingsClicked = onSettingsClicked,
                onCreditClicked = onCreditClicked,
                onDocsClicked = onDocsClicked,
                onOnboardingClicked = onOnboardingCompletedClicked,
                onCardPaymentCalendarClicked = onCardPaymentCalendarClicked,
                onFuelTrackerClicked = onFuelTrackerClicked,
                onMyTripsClicked = onMyTripsClicked
            )
        }
        composable(DayMateRoute.Onboarding.route) {
            OnboardingScreen(
                onCompleted = onOnboardingCompletedClicked
            )
        }
        composable(DayMateRoute.Docs.route) {
            DocsScreen(
                state = docsState
            )
        }
        composable(DayMateRoute.CardPaymentCalendar.route) {
            CardPaymentCalendarScreen(
                settingsState,
                onCreditClicked
            )
        }
        composable(DayMateRoute.FinancialProjection.route) {
            FinancialProjectionScreen(
                settingsState,
                financialProjectionState = financialProjectionState,
                onCalculateFinancialProjection = onCalculateFinancialProjection,
                onCreditClicked = onCreditClicked
            )
        }
        composable(DayMateRoute.AddNewCard.route) {
            AddCardScreen(
                onAction = {},
                onCommit = { /*TODO*/ },
                onCancel = {
                    navController.popBackStack()
                }
            )
        }
        composable(DayMateRoute.Settings.route) {
            SettingsScreen(
                settingsState,
                onAction = onSettingsAction,
                onCreditClicked = onCreditClicked,
                onAddNewCardClicked = onAddNewCardClicked,
                onFinancialProjectionClicked = onFinancialProjectionClick
            )
        }
        composable(DayMateRoute.TripPlanner.route) {
            TripPlannerScreen(
                onCreditClicked = onCreditClicked
            )
        }
        composable(DayMateRoute.FuelTracker.route) {
            FuelTrackerScreen(
                fuelTrackerState,
                onCreditClicked = onCreditClicked
            )
        }
    }
}

sealed class DayMateRoute(val route: String) {
    data object Onboarding : DayMateRoute("onboarding")
    data object Landing : DayMateRoute("landing")
    data object Docs : DayMateRoute("docs")
    data object FinancialProjection : DayMateRoute("financial_projection")
    data object Settings : DayMateRoute("settings")
    data object AddNewCard : DayMateRoute("add_new_card")
    data object CardPaymentCalendar : DayMateRoute("card_payment_calendar")
    data object TripPlanner: DayMateRoute("trip_planner")
    data object FuelTracker: DayMateRoute("fuel_tracker")
}