package com.cb.meapps.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fuel_tracker")
data class FuelTrackerEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val totalKM: Float,
    val totalPrice: Float,
    val liters: Float
)
