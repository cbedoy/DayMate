package com.cb.meapps.domain.usecase

import com.cb.meapps.data.repository.AdditionalPaymentsRepository
import com.cb.meapps.domain.model.AdditionalPayment
import com.cb.meapps.data.repository.PreferencesDelegate
import javax.inject.Inject

class DeleteAdditionalPaymentUseCase @Inject constructor(
    private val additionalPaymentsRepository: AdditionalPaymentsRepository
) {
    suspend operator fun invoke(name: String){
        val paymentEntity = additionalPaymentsRepository.getByName(name)
        paymentEntity?.let { entity ->
            additionalPaymentsRepository.delete(entity)
        }
    }
}