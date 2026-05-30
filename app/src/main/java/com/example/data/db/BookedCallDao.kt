package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.BookedCall
import kotlinx.coroutines.flow.Flow

@Dao
interface BookedCallDao {
    @Query("SELECT * FROM booked_calls ORDER BY timestamp DESC")
    fun getAllBookedCalls(): Flow<List<BookedCall>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookedCall(call: BookedCall): Long

    @Query("DELETE FROM booked_calls WHERE id = :id")
    suspend fun deleteBookedCallById(id: Int)
}
