package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "estimate_quotes")
data class EstimateQuote(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val clientName: String,
    val clientEmail: String,
    val projectScope: String,
    val description: String,
    val calculatedPrice: Int,
    val timestamp: Long = System.currentTimeMillis()
)
