package com.cb.meapps.data.repository

import com.cb.meapps.data.dao.CardDao
import com.cb.meapps.data.model.CardEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CardsRepository @Inject constructor(
    private val cardDao: CardDao
) {
    fun getAllCards(): Flow<List<CardEntity>> = cardDao.getAllCards()

    suspend fun saveCard(card: CardEntity) {
        cardDao.insertCard(card)
    }

    suspend fun deleteCard(card: CardEntity) {
        cardDao.deleteCard(card)
    }
}
