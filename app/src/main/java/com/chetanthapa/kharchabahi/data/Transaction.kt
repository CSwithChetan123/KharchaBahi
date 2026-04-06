package com.chetanthapa.kharchabahi.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val time: Long,
    val amount: Double,
    val type: String,
    val category: String,
    val note: String
)
