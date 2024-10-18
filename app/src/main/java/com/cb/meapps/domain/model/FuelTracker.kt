package com.cb.meapps.domain.model

data class FuelTracker(
    val id: Int,
    val totalKM: Float,
    val totalPrice: Float,
    val liters: Float
) {
    val kmPerLiter: Float
        get() = totalKM / liters

    val pricePerKm: Float
        get() = totalPrice / totalKM
}

data class SummaryFullTracker(
    val totalKM: Float,
    val totalPrice: Float,
    val totalLiters: Float
) {
    val totalKmPerLiter: Float
        get() = totalKM / totalLiters
}