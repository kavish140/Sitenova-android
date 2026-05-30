package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.EstimateQuote
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {
    @Query("SELECT * FROM estimate_quotes ORDER BY timestamp DESC")
    fun getAllQuotes(): Flow<List<EstimateQuote>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuote(quote: EstimateQuote): Long

    @Query("DELETE FROM estimate_quotes WHERE id = :id")
    suspend fun deleteQuoteById(id: Int)
}
