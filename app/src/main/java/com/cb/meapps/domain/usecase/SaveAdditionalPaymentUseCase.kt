package com.cb.meapps.domain.usecase

import com.cb.meapps.data.model.AdditionalPaymentEntity
import com.cb.meapps.data.repository.AdditionalPaymentsRepository
import javax.inject.Inject

class SaveAdditionalPaymentUseCase @Inject constructor(
    private val additionalPaymentsRepository: AdditionalPaymentsRepository
) {
    suspend operator fun invoke(name: String, day: Int, value: Float) {
        additionalPaymentsRepository.save(
            AdditionalPaymentEntity(name = name, day = day, value = value)
        )
    }
}