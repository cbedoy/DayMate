package com.cb.meapps.domain.usecase

import com.cb.meapps.data.repository.PreferencesDelegate
import com.cb.meapps.domain.asMoney
import com.cb.meapps.domain.fake.getFakeCards
import com.cb.meapps.domain.model.Card
import com.cb.meapps.domain.model.CardPayment
import com.cb.meapps.domain.model.CombinedProjection
import com.cb.meapps.domain.model.DateInfo
import com.cb.meapps.domain.model.ProjectionDay
import com.cb.meapps.domain.toDoubleOrZero
import javax.inject.Inject

class GetMoneyMapUseCase @Inject constructor(
    private val preferencesDelegate: PreferencesDelegate,
    private val getFinancialProjectionUseCase: GetFinancialProjectionUseCase,
    private val getCardCalendarUseCase: GetCardCalendarUseCase
) {
    operator fun invoke(days: Int): List<CombinedProjection> {
        val cards = getFakeCards()
        val projections = getFinancialProjectionUseCase(
            preferencesDelegate.getInitialSavings().toDoubleOrZero(),
            preferencesDelegate.getAnnualInterestRate().toDoubleOrZero(),
            preferencesDelegate.getBiweeklyPayment().toDoubleOrZero(),
            days
        )
        val cardDates = getCardCalendarUseCase.invoke(days)

        return combineProjections(projections, cardDates, cards)
    }

    private fun combineProjections(
        projections: List<ProjectionDay>,
        cardDates: List<DateInfo>,
        cards: List<Card>
    ): List<CombinedProjection> {
        val combinedList = mutableListOf<CombinedProjection>()

        val cardPaymentsByDate = mutableMapOf<String, MutableList<CardPayment>>()


        for (dateInfo in cardDates) {
            val dueCards = cards.filter { it.dueDate == dateInfo.day }
            if (dueCards.isNotEmpty()) {
                val dateKey = dateInfo.date
                val payments = dueCards.map { CardPayment(it.name, it.debt) }
                cardPaymentsByDate[dateKey] = payments.toMutableList()
            }
        }

        var totalSavings = projections.firstOrNull()?.current?.toDoubleOrNull() ?: 0.0

        for (projection in projections) {
            val date = projection.date
            val cardPayments = cardPaymentsByDate[date] ?: emptyList()
            var adjustedTotalSavings = totalSavings

            // subtract if apply
            for (cardPayment in cardPayments) {
                adjustedTotalSavings -= cardPayment.cardDebt
            }

            totalSavings = adjustedTotalSavings + projection.paymentToday.toDoubleOrZero()  + projection.dailyInterest.toDoubleOrZero()

            val combinedProjection = CombinedProjection(
                date = date,
                current = projection.current,
                paymentToday = projection.paymentToday,
                dailyInterest = projection.dailyInterest,
                accumulatedInterest = projection.accumulatedInterest,
                totalSavings = adjustedTotalSavings.asMoney(),
                isPaymentDay = projection.isPaymentDay,
                cardPayments = cardPayments
            )

            combinedList.add(combinedProjection)
        }

        return combinedList
    }

}