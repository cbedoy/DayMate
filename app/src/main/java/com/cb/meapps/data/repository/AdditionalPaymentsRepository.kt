package com.cb.meapps.data.repository

import com.cb.meapps.data.dao.AdditionalPaymentDao
import com.cb.meapps.data.model.AdditionalPaymentEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AdditionalPaymentsRepository @Inject constructor(
    private val dao: AdditionalPaymentDao
) {
    fun getAll(): Flow<List<AdditionalPaymentEntity>> = dao.getAll()

    fun getByName(name: String) = dao.getByName(name)

    suspend fun save(entity: AdditionalPaymentEntity) {
        dao.insert(entity)
    }

    suspend fun delete(entity: AdditionalPaymentEntity) {
        dao.delete(entity)
    }
}