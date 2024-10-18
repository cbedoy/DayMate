package com.cb.meapps.data

import com.cb.meapps.data.dao.FuelTrackerDao
import com.cb.meapps.data.model.FuelTrackerEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FuelTrackerRepository @Inject constructor(
    private val fuelTrackerDao: FuelTrackerDao
) {
    fun getAll(): Flow<List<FuelTrackerEntity>> = fuelTrackerDao.getAll()

    suspend fun save(document: FuelTrackerEntity) {
        fuelTrackerDao.insert(document)
    }

    suspend fun delete(document: FuelTrackerEntity) {
        fuelTrackerDao.delete(document)
    }

    suspend fun get(fuelTrackerId: Int): FuelTrackerEntity? {
        return fuelTrackerDao.getFuelTracker(
            fuelTrackerId
        )
    }
}