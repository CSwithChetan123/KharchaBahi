package com.chetanthapa.kharchabahi.data

import kotlinx.coroutines.flow.Flow

class TransactionRepository(private val noteDao: TransactionDao) {
    val allTransactions: Flow<List<Transaction>> = noteDao.getAllTransactions()

    suspend fun insertTransaction(transaction: Transaction) {
        noteDao.insertTransaction(transaction)
    }
    suspend fun deleteTransaction(transaction: Transaction) {
        noteDao.deleteTransaction(transaction)
    }
    suspend fun updateTransaction(transaction: Transaction) {
        noteDao.updateTransaction(transaction)
    }


}