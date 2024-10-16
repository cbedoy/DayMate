package com.cb.meapps.domain.usecase

import com.cb.meapps.data.CardsRepository
import com.cb.meapps.domain.fake.IsFakeDataEnabledUseCase
import com.cb.meapps.domain.fake.getFakeCards
import com.cb.meapps.domain.model.Card
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCardsUseCase @Inject constructor(
    private val isFakeDataEnabledUseCase: IsFakeDataEnabledUseCase,
    private val cardsRepository: CardsRepository
) {
    operator fun invoke(): Flow<List<Card>> {
        return cardsRepository.getAllCards()
            .map { entityList ->
                if (isFakeDataEnabledUseCase()) {
                    getFakeCards()
                } else {
                    entityList.map { entity ->
                        Card(
                            name = entity.name,
                            cutOffDate = entity.cutOffDate,
                            dueDate = entity.dueDate
                        )
                    }
                }
            }
            .distinctUntilChanged()
            .catch {
                emit(emptyList())
            }
    }
}