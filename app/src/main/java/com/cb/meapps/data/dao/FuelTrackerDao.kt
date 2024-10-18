package com.cb.meapps.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cb.meapps.data.model.FuelTrackerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FuelTrackerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(fuelTracker: FuelTrackerEntity)

    @Query("SELECT * FROM fuel_tracker")
    fun getAll(): Flow<List<FuelTrackerEntity>>

    @Query("SELECT * FROM fuel_tracker WHERE id = :fuelTrackerId")
    fun getFuelTracker(fuelTrackerId: Int): FuelTrackerEntity?

    @Delete
    suspend fun delete(document: FuelTrackerEntity)
}