package com.cb.meapps.domain.model

data class CombinedProjection(
    val date: String,
    val current: String,
    val paymentToday: String,
    val dailyInterest: String,
    val accumulatedInterest: String,
    val totalSavings: String,
    val isPaymentDay: Boolean,
    val cardPayments: List<CardPayment>
)

data class CardPayment(
    val cardName: String,
    val cardDebt: Float
)