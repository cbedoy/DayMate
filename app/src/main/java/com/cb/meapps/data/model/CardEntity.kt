package com.cb.meapps.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards")
data class CardEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val cutOffDate: Int,
    val dueDate: Int
)