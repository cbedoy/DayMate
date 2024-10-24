package com.cb.meapps.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cb.meapps.data.model.AdditionalPaymentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AdditionalPaymentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: AdditionalPaymentEntity)

    @Query("SELECT * FROM additional_payment")
    fun getAll(): Flow<List<AdditionalPaymentEntity>>

    @Query("SELECT * FROM additional_payment WHERE name = :name")
    fun getByName(name: String): AdditionalPaymentEntity?

    @Delete
    suspend fun delete(entity: AdditionalPaymentEntity)
}