package com.cb.meapps.domain.model

data class ProjectionDay(
    val year: Int,
    val date: String,
    val current: String,
    val paymentToday: String,
    val dailyInterest: String,
    val accumulatedInterest: String,
    val totalSavings: String,
    val isPaymentDay: Boolean
)