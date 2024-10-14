package com.cb.meapps.presentation.viewmodel.financial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cb.meapps.presentation.ui.screens.ProjectionDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class FinancialProjectionViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(FinancialProjectionState())
    val state: StateFlow<FinancialProjectionState> = _state.asStateFlow()

    fun calculateProjections(
        initialSavings: Double,
        annualInterestRate: Double,
        biweeklyPayment: Double,
        days: Int = 360
    ) {
        viewModelScope.launch {
            val calendar = Calendar.getInstance()
            var totalSavings = initialSavings
            var accumulatedInterest = 0.0

            val dateFormat = SimpleDateFormat("d MMM", Locale.getDefault())
            val annualInterestDecimal = annualInterestRate / 100.0
            val dailyInterestRate = annualInterestDecimal / 360.0

            val projections = (0..days).map { dayIndex ->

                val current = totalSavings

                val newCal = calendar.clone() as Calendar
                newCal.add(Calendar.DAY_OF_YEAR, dayIndex)

                val dayOfMonth = newCal.get(Calendar.DAY_OF_MONTH)
                var paymentToday = 0.0

                val month = newCal.get(Calendar.MONTH) + 1

                val isLastDayOfFebruary = if (month == Calendar.FEBRUARY) {
                    val lastDayOfFebruary = newCal.getActualMaximum(Calendar.DAY_OF_MONTH)
                    dayOfMonth == lastDayOfFebruary
                } else {
                    false
                }

                val isPaymentDay = (dayOfMonth == 15 || dayOfMonth == 30 || isLastDayOfFebruary)

                if (isPaymentDay) {
                    paymentToday = biweeklyPayment
                }

                val dailyInterest = totalSavings * dailyInterestRate
                accumulatedInterest += dailyInterest
                totalSavings += paymentToday + dailyInterest

                val formattedDate = dateFormat.format(newCal.time).uppercase()

                ProjectionDay(
                    date = formattedDate,
                    current = current.asMoney(),
                    paymentToday = paymentToday.asMoney(),
                    dailyInterest = dailyInterest.asMoney(),
                    accumulatedInterest = accumulatedInterest.asMoney(),
                    totalSavings = totalSavings.asMoney(),
                    isPaymentDay = isPaymentDay
                )
            }

            _state.update {
                it.copy(projectionDays = projections)
            }
        }
    }
}

private fun Double.asMoney() = "\$${String.format("%,.0f", this)}"

data class FinancialProjectionState(
    val projectionDays: List<ProjectionDay> = emptyList()
)