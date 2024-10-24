package com.cb.meapps.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "additional_payment")
data class AdditionalPaymentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val day: Int,
    val value: Float
)