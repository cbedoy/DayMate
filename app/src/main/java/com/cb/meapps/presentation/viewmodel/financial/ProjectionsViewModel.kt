package com.cb.meapps.presentation.viewmodel.financial

import androidx.lifecycle.ViewModel
import com.cb.meapps.data.PreferencesDelegate
import com.cb.meapps.domain.model.CombinedProjection
import com.cb.meapps.domain.model.DateInfo
import com.cb.meapps.domain.model.ProjectionDay
import com.cb.meapps.domain.usecase.GetCardCalendarUseCase
import com.cb.meapps.domain.usecase.GetFinancialProjectionUseCase
import com.cb.meapps.domain.usecase.GetMoneyMapUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProjectionsViewModel @Inject constructor(
    private val getFinancialProjectionUseCase: GetFinancialProjectionUseCase,
    private val getCardCalendarUseCase: GetCardCalendarUseCase,
    private val getMoneyMapUseCase: GetMoneyMapUseCase,
    private val sharedPreferencesDelegate: PreferencesDelegate
) : ViewModel() {

    private val _state = MutableStateFlow(ProjectionsState())
    val state: StateFlow<ProjectionsState> = _state.asStateFlow()

    fun dispatch(action: ProjectionsAction) {
        when(action) {
            is ProjectionsAction.CalculateFinancialProjection -> {

                val days = sharedPreferencesDelegate.getProjectionDays().toIntOrNull() ?: 360

                val projections = getFinancialProjectionUseCase(
                    action.initialSavings,
                    action.annualInterestRate,
                    action.biweeklyPayment,
                    days
                )
                _state.update {
                    it.copy(projectionDays = projections)
                }
            }
            is ProjectionsAction.CalculateCardPaymentCalendar -> {
                val days = sharedPreferencesDelegate.getProjectionDays().toIntOrNull() ?: 360

                val generateNextDays = getCardCalendarUseCase(days)
                val datesGroupedByMonth = generateNextDays.groupBy { it.monthName + " " + it.year }
                _state.update {
                    it.copy(
                        datesGroupedByMonth = datesGroupedByMonth
                    )
                }
            }

            ProjectionsAction.LoadMoneyMap -> {
                val combinedProjections = getMoneyMapUseCase(360)
                _state.update {
                    it.copy(
                        combinedProjections = combinedProjections
                    )
                }
            }
        }
    }
}

data class ProjectionsState(
    val projectionDays: List<ProjectionDay> = emptyList(),
    val datesGroupedByMonth: Map<String, List<DateInfo>> = emptyMap(),
    val combinedProjections: List<CombinedProjection> = emptyList()
)

sealed class ProjectionsAction {
    data class CalculateFinancialProjection(
        val initialSavings: Double,
        val annualInterestRate: Double,
        val biweeklyPayment: Double,
    ) : ProjectionsAction()

    data object CalculateCardPaymentCalendar: ProjectionsAction()

    data object LoadMoneyMap: ProjectionsAction()
}