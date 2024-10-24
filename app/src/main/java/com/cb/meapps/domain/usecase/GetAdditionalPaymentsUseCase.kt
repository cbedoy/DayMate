package com.cb.meapps.domain.usecase

import com.cb.meapps.data.repository.AdditionalPaymentsRepository
import com.cb.meapps.data.repository.CardsRepository
import com.cb.meapps.domain.fake.IsFakeDataEnabledUseCase
import com.cb.meapps.domain.fake.getFakeAdditionalPayments
import com.cb.meapps.domain.fake.getFakeCards
import com.cb.meapps.domain.model.AdditionalPayment
import com.cb.meapps.domain.model.Card
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAdditionalPaymentsUseCase @Inject constructor(
    private val isFakeDataEnabledUseCase: IsFakeDataEnabledUseCase,
    private val repository: AdditionalPaymentsRepository
) {
    suspend operator fun invoke(): Flow<List<AdditionalPayment>> {
        return repository.getAll()
            .map { entityList ->
                if (isFakeDataEnabledUseCase()) {
                    getFakeAdditionalPayments()
                } else {
                    entityList.map { entity ->
                        AdditionalPayment(
                            id = entity.id,
                            name = entity.name,
                            day = entity.day,
                            value = entity.value
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