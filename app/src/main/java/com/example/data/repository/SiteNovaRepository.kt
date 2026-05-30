package com.example.data.repository

import com.example.data.db.BookedCallDao
import com.example.data.db.QuoteDao
import com.example.data.model.BookedCall
import com.example.data.model.EstimateQuote
import kotlinx.coroutines.flow.Flow

class SiteNovaRepository(
    private val quoteDao: QuoteDao,
    private val bookedCallDao: BookedCallDao
) {
    val allQuotes: Flow<List<EstimateQuote>> = quoteDao.getAllQuotes()
    val allBookedCalls: Flow<List<BookedCall>> = bookedCallDao.getAllBookedCalls()

    suspend fun saveQuote(quote: EstimateQuote): Long {
        return quoteDao.insertQuote(quote)
    }

    suspend fun deleteQuote(id: Int) {
        quoteDao.deleteQuoteById(id)
    }

    suspend fun saveBookedCall(call: BookedCall): Long {
        return bookedCallDao.insertBookedCall(call)
    }

    suspend fun deleteBookedCall(id: Int) {
        bookedCallDao.deleteBookedCallById(id)
    }
}
