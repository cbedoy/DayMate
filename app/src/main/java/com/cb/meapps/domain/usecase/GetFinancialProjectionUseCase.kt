package com.cb.meapps.domain.usecase

import com.cb.meapps.data.repository.PreferencesDelegate
import com.cb.meapps.domain.asMoney
import com.cb.meapps.domain.model.ProjectionDay
import com.cb.meapps.domain.toDoubleOrZero
import com.cb.meapps.domain.toIntOrDefault
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class GetFinancialProjectionUseCase @Inject constructor(
    private val preferencesDelegate: PreferencesDelegate
) {
    operator fun invoke(): List<ProjectionDay> {
        val days = preferencesDelegate.getProjectionDays().toIntOrDefault(360)
        val initialSavings = preferencesDelegate.getInitialSavings().toDoubleOrZero()
        val annualInterestRate = preferencesDelegate.getAnnualInterestRate().toDoubleOrZero()
        val biweeklyPayment = preferencesDelegate.getBiweeklyPayment().toDoubleOrZero()

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

            val year = newCal.get(Calendar.YEAR)
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
                year = year,
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