package com.cb.meapps.presentation.ui

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cb.meapps.presentation.ui.screens.AddCardScreen
import com.cb.meapps.presentation.ui.screens.AdditionalPaymentScreen
import com.cb.meapps.presentation.ui.screens.CardPaymentCalendarScreen
import com.cb.meapps.presentation.ui.screens.DocsScreen
import com.cb.meapps.presentation.ui.screens.EditFuelTrackerScreen
import com.cb.meapps.presentation.ui.screens.FinancialProjectionScreen
import com.cb.meapps.presentation.ui.screens.FuelTrackerScreen
import com.cb.meapps.presentation.ui.screens.LandingScreen
import com.cb.meapps.presentation.ui.screens.MoneyMapScreen
import com.cb.meapps.presentation.ui.screens.OnboardingScreen
import com.cb.meapps.presentation.ui.screens.SettingsScreen
import com.cb.meapps.presentation.ui.screens.TripPlannerScreen
import com.cb.meapps.presentation.viewmodel.DocsState
import com.cb.meapps.presentation.viewmodel.FuelTrackerAction
import com.cb.meapps.presentation.viewmodel.FuelTrackerState
import com.cb.meapps.presentation.viewmodel.financial.ProjectionsAction
import com.cb.meapps.presentation.viewmodel.financial.ProjectionsState
import com.cb.meapps.presentation.viewmodel.settings.SettingsAction
import com.cb.meapps.presentation.viewmodel.settings.SettingsState

@Composable
fun DayMateContainer(
    settingsState: SettingsState,
    docsState: DocsState,
    projectionsState: ProjectionsState,
    fuelTrackerState: FuelTrackerState,
    onProjectionsAction: (ProjectionsAction) -> Unit,
    onFuelTrackerAction: (FuelTrackerAction) -> Unit,
    onSettingsAction: (SettingsAction) -> Unit
) {
    val navController = rememberNavController()

    val onNavigationClicked: (DayMateRoute) -> Unit = { route ->
        when(route) {
            is DayMateRoute.Onboarding -> {
                // sideEffects?
                onSettingsAction(SettingsAction.SkipOnboarding)
            }
            else -> {

            }
        }
        navController.navigate(route.route)
    }

    // Navigate to the onboarding screen if the user hasn't skipped it
    val initialRoute = if (settingsState.skipOnboarding) {
        DayMateRoute.Landing.route
    } else {
        DayMateRoute.Onboarding.route
    }

    NavHost(
        navController,
        startDestination = initialRoute,
        enterTransition = { provideEnterTransition() },
        exitTransition = { provideExitTransition() },
        popEnterTransition = { providePopEnterTransition() },
        popExitTransition = { providePopExitTransition() },
        modifier = Modifier
    ) {
        composable(DayMateRoute.Landing.route) {
            LandingScreen(
                onNavigationClicked = onNavigationClicked
            )
        }
        composable(DayMateRoute.Onboarding.route) {
            OnboardingScreen(
                onNavigationClicked = onNavigationClicked
            )
        }
        composable(DayMateRoute.Docs.route) {
            DocsScreen(
                state = docsState
            )
        }
        composable(DayMateRoute.CardPaymentCalendar.route) {

            LaunchedEffect(settingsState) {
                onProjectionsAction(ProjectionsAction.CalculateCardPaymentCalendar)
            }

            CardPaymentCalendarScreen(
                settingsState = settingsState,
                projectionsState = projectionsState,
                onProjectionsAction = onProjectionsAction
            )
        }
        composable(DayMateRoute.FinancialProjection.route) {
            FinancialProjectionScreen(
                projectionsState = projectionsState,
                onProjectionsAction = onProjectionsAction,
            )
        }
        composable(DayMateRoute.AddNewCard.route) {
            AddCardScreen(
                settingsState = settingsState,
                onAction = onSettingsAction,
            )
        }
        composable(DayMateRoute.Settings.route) {
            SettingsScreen(
                settingsState,
                onAction = onSettingsAction,
                onNavigationClicked = onNavigationClicked
            )
        }
        composable(DayMateRoute.TripPlanner.route) {
            TripPlannerScreen()
        }
        composable(DayMateRoute.FuelTracker.route) {
            FuelTrackerScreen(
                fuelTrackerState,
                onNavigateToEdit = {
                    navController.navigate(
                        DayMateRoute.EditFuelTracker.route.replace(FuelTrackerIdKey, it.id.toString())
                    )
                }
            )
        }
        composable(DayMateRoute.MoneyMap.route) {
            LaunchedEffect(settingsState) {
                onProjectionsAction(ProjectionsAction.LoadMoneyMap)
            }
            MoneyMapScreen(
                projectionsState
            )
        }
        composable(DayMateRoute.AdditionalPayments.route) {
            AdditionalPaymentScreen(settingsState, onSettingsAction)
        }
        composable(
            DayMateRoute.EditFuelTracker.route
        ) { navBackStackEntry ->
            val fuelTrackerId = navBackStackEntry.arguments?.getString("fuelTrackerId")?.toIntOrNull()

            LaunchedEffect(fuelTrackerId) {
                if (fuelTrackerId != null) {
                    onFuelTrackerAction(FuelTrackerAction.LoadFuelTracker(fuelTrackerId))
                }
            }

            EditFuelTrackerScreen(
                state = fuelTrackerState,
                onAction = onFuelTrackerAction
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
    data object EditFuelTracker: DayMateRoute("edit_fuel_tracker/{fuelTrackerId}")
    data object MoneyMap: DayMateRoute("money_map")
    data object AdditionalPayments: DayMateRoute("additional_payments")
}

private const val FuelTrackerIdKey = "{fuelTrackerId}"

fun provideEnterTransition(): EnterTransition {
    return slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(500))
}

fun provideExitTransition(): ExitTransition {
    return slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(500))
}

fun providePopEnterTransition(): EnterTransition {
    return slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(500))
}
fun providePopExitTransition(): ExitTransition {
    return slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(500))
}
