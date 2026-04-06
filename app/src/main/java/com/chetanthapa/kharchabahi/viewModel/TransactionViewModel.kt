package com.chetanthapa.kharchabahi.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.chetanthapa.kharchabahi.data.Transaction
import com.chetanthapa.kharchabahi.data.TransactionDatabase
import com.chetanthapa.kharchabahi.data.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TransactionViewModel(application: Application): AndroidViewModel(application) {
    private val repository: TransactionRepository = TransactionRepository(TransactionDatabase.getDatabase(application).transactionDao())
    val allTransactions = repository.allTransactions.stateIn(
        scope = viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )



    fun insertTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTransaction(transaction)
        }

    }

    fun updateTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTransaction(transaction)
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTransaction(transaction)
        }
    }

    fun totalIncome(transactions: List<Transaction>): Double {
        return transactions.filter { it.type == "Income" }.sumOf { it.amount }
    }

    fun totalExpense(transactions: List<Transaction>): Double {
        return transactions.filter { it.type == "Expense" }.sumOf { it.amount }
    }

    fun totalBalance(transactions: List<Transaction>): Double {
        return  totalIncome(transactions) - totalExpense(transactions)
    }

}