package com.cb.meapps.domain.usecase

import com.cb.meapps.data.CardsRepository
import com.cb.meapps.data.model.CardEntity
import kotlinx.coroutines.delay
import javax.inject.Inject

class AddNewCardUseCase @Inject constructor(
    private val cardsRepository: CardsRepository
) {
    suspend operator fun invoke(name: String, cutOffDate: Int, dueDate: Int) {
        delay(3_00)
        cardsRepository.saveCard(
            CardEntity(
                name = name,
                cutOffDate = cutOffDate,
                dueDate = dueDate
            )
        )
    }
}