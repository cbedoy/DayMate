package com.cb.meapps.domain.fake

import com.cb.meapps.domain.model.Card
import com.cb.meapps.domain.model.FuelTracker

fun getFakeCards(): List<Card> {
    return listOf(
        Card(
            name = "Invex",
            cutOffDate = 20,
            dueDate = 10
        ),
        Card(
            name = "Hey Banco",
            cutOffDate = 25,
            dueDate = 15
        ),
        Card(
            name = "Rappi",
            cutOffDate = 15,
            dueDate = 5
        ),
        Card(
            name = "Citibanamex",
            cutOffDate = 14,
            dueDate = 4
        )
    )
}

fun getFakeTracks(): List<FuelTracker> {
    return listOf(
        FuelTracker(560f, 988f, 40f),
        FuelTracker(550f, 1000f, 42.5f),
        FuelTracker(120f, 200f, 8.1f),
        FuelTracker(265f, 1000f, 42.3f),
    )
}