package com.cb.meapps.presentation.viewmodel.financial

import androidx.lifecycle.ViewModel
import com.cb.meapps.domain.usecase.GetFinancialProjectionUseCase
import com.cb.meapps.presentation.ui.screens.ProjectionDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProjectionsViewModel @Inject constructor(
    private val getFinancialProjectionUseCase: GetFinancialProjectionUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProjectionsState())
    val state: StateFlow<ProjectionsState> = _state.asStateFlow()

    fun dispatch(action: ProjectionsAction) {
        when(action) {
            is ProjectionsAction.CalculateFinancialProjection -> {
                val projections = getFinancialProjectionUseCase(
                    action.initialSavings,
                    action.annualInterestRate,
                    action.biweeklyPayment,
                    action.days
                )
                _state.update {
                    it.copy(projectionDays = projections)
                }
            }
        }
    }
}

data class ProjectionsState(
    val projectionDays: List<ProjectionDay> = emptyList()
)

sealed class ProjectionsAction {
    data class CalculateFinancialProjection(
        val initialSavings: Double,
        val annualInterestRate: Double,
        val biweeklyPayment: Double,
        val days: Int = 360
    ) : ProjectionsAction()
}