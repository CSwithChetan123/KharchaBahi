package com.chetanthapa.kharchabahi.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.chetanthapa.kharchabahi.viewModel.TransactionViewModel

@Composable
fun StatsScreen(navController: NavHostController) {
    val viewModel: TransactionViewModel = viewModel()

    var selectedTab by remember { mutableStateOf("Expense") }
    var isIncomeSelected by remember { mutableStateOf(false) }
    var isExpenseSelected by remember { mutableStateOf(true) }


    val transactions by viewModel.allTransactions.collectAsState(initial = emptyList())
    val totalIncome = viewModel.totalIncome(transactions)
    val totalExpense = viewModel.totalExpense(transactions)



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp, top = 30.dp)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly

        ) {
            //IncomeBtn
            Button(
                onClick = {
                    selectedTab = "Income"
                    isIncomeSelected = true
                    isExpenseSelected = false
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if(isIncomeSelected) Color.Blue else Color.Gray,

                    ),
                content = {
                    Text(text = "Income ₹$totalIncome")
                }


            )

            //Expense
            Button(
                onClick = {
                    selectedTab = "Expense"
                    isIncomeSelected = false
                    isExpenseSelected = true
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if(isExpenseSelected) Color.Blue else Color.Gray,

                    ),
                content = {
                    Text(text = "Expense ₹$totalExpense")
                }


            )
        }
        Spacer(modifier = Modifier.height(30.dp))

        val filter = transactions.filter { it.type == selectedTab }
        val groupedByCategory = filter.groupBy { it.category }


        LazyColumn(

        ) {
            groupedByCategory.forEach { (category, categoryTransactions) ->
                item {
                    val categoryAmount = categoryTransactions.sumOf { it.amount }
                    val percentage = if(selectedTab == "Income"){
                        ((categoryAmount / totalIncome) * 100).toInt()
                    }
                    else{
                        ((categoryAmount / totalExpense) * 100).toInt()
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly

                    ) {
                        Text(
                            text ="$category",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "₹$categoryAmount",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center

                        )
                        Text(
                            text = "$percentage%",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center

                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))



                }
            }
        }

    }

}