package com.cb.meapps.presentation.viewmodel.financial

import androidx.lifecycle.ViewModel
import com.cb.meapps.data.repository.PreferencesDelegate
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
    private val getMoneyMapUseCase: GetMoneyMapUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProjectionsState())
    val state: StateFlow<ProjectionsState> = _state.asStateFlow()

    fun dispatch(action: ProjectionsAction) {
        when(action) {
            is ProjectionsAction.CalculateFinancialProjection -> {
                val projections = getFinancialProjectionUseCase()
                _state.update {
                    it.copy(projectionDays = projections)
                }
            }
            is ProjectionsAction.CalculateCardPaymentCalendar -> {
                val generateNextDays = getCardCalendarUseCase()
                val datesGroupedByMonth = generateNextDays.groupBy { it.monthName + " " + it.year }
                _state.update {
                    it.copy(
                        datesGroupedByMonth = datesGroupedByMonth
                    )
                }
            }

            ProjectionsAction.LoadMoneyMap -> {
                val combinedProjections = getMoneyMapUseCase()
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
    data object CalculateFinancialProjection : ProjectionsAction()

    data object CalculateCardPaymentCalendar: ProjectionsAction()

    data object LoadMoneyMap: ProjectionsAction()
}