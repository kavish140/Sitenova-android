package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "booked_calls")
data class BookedCall(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val clientName: String,
    val clientEmail: String,
    val phoneNumber: String,
    val bookingDate: String,
    val timeSlot: String,
    val messageNote: String,
    val timestamp: Long = System.currentTimeMillis()
)
