package com.cb.meapps.domain.usecase

import com.cb.meapps.domain.asMoney
import com.cb.meapps.presentation.ui.screens.ProjectionDay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class GetFinancialProjectionUseCase @Inject constructor() {
    operator fun invoke(
        initialSavings: Double,
        annualInterestRate: Double,
        biweeklyPayment: Double,
        days: Int = 360
    ): List<ProjectionDay> {
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

            val month = newCal.get(Calendar.MONTH)

            val isLastDayOfFebruary = if (month == Calendar.FEBRUARY) {
                val lastDayOfFebruary = newCal.getActualMaximum(Calendar.DAY_OF_MONTH)
                dayOfMonth == lastDayOfFebruary
            } else {
                false
            }

            val isPaymentDay = if (dayIndex == 0) {
                false
            } else {
                (dayOfMonth == 15 || dayOfMonth == 30 || isLastDayOfFebruary)
            }

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

        return projections
    }
}