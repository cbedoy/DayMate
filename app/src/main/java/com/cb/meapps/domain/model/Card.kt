package com.cb.meapps.domain.model

data class Card(
    val id: Int,
    val name: String,
    val cutOffDate: Int,
    val dueDate: Int,
    val debt: Float
)