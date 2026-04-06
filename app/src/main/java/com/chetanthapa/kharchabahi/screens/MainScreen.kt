package com.chetanthapa.kharchabahi.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.chetanthapa.kharchabahi.navigation.NavigationRoutes
import com.chetanthapa.kharchabahi.viewModel.TransactionViewModel
import java.text.SimpleDateFormat


@Composable
fun MainScreen(navController: NavHostController) {
    var selectedTab by remember { mutableIntStateOf(0) }


    Scaffold(
        bottomBar = {
            NavigationBar {
                //Transaction
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = {
                        Icon(Icons.Default.Receipt, contentDescription = "Add")

                    },
                    label = {
                        Text(text = "Trans.")
                    }
                )

                //Stats
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = {
                        selectedTab = 1

                    },
                    icon = {
                        Icon(Icons.Default.BarChart, contentDescription = "Stats")

                    },
                    label = {
                        Text(text = "Stats")
                    }
                )

                //Budget
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = {
                        Icon(Icons.Default.Savings, contentDescription = "Saving")

                    },
                    label = {
                        Text(text = "Saving")
                    }
                )

            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(NavigationRoutes.AddEdit())
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }

        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)

        ) {
            when (selectedTab) {
                0 -> {
                    TransactionContent(navController)
                }

                1 -> {
                    StatsScreen(navController)
                }

                2 -> {
                    BudgetScreen(navController)
                }
            }
        }

    }
}

@Composable
fun TransactionContent(navController: NavHostController) {
    var searchText by remember { mutableStateOf("") }

    val viewModel: TransactionViewModel = viewModel()
    val transactions by viewModel.allTransactions.collectAsState(initial = emptyList())
    val totalIncome = viewModel.totalIncome(transactions)
    val totalExpense = viewModel.totalExpense(transactions)
    val totalBalance = viewModel.totalBalance(transactions)

    val filteredTransactions = if(searchText.isNotEmpty()){
        transactions.filter{ it.category.contains(searchText, ignoreCase = true) || it.note.contains(searchText, ignoreCase = true)}
    }
    else {
        transactions
    }


    Column(
        modifier = Modifier
            .padding(start = 10.dp)

    ) {
            OutlinedTextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                },
                label = { Text(text = "\uD83D\uDD0E\uFE0E Search") },

                )
        Spacer(modifier = Modifier.height(10.dp))
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = Color.LightGray
        )
        //Income, Expense, Total (Row Header)
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceEvenly

        ) {
            //Income
            Column(
            ) {
                //IncomeText
                Text(
                    text = "Income",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "+ ₹$totalIncome",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Blue
                )

            }
            //Expenses
            Column(
            ) {
                //expenseText
                Text(
                    text = "Expense",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "- ₹$totalExpense",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Red
                )

            }
            // Total
            //Income
            Column(
            ) {
                //IncomeText
                Text(
                    text = "Total",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "$totalBalance",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )

            }

        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = Color.LightGray
        )

        //Transactions dateWise
        LazyColumn(
            modifier = Modifier
                .padding(10.dp)
        ) {
            val groupedTransactions = filteredTransactions.groupBy { SimpleDateFormat("dd/MM/yyyy").format(it.time) }
            groupedTransactions.forEach { (date, dayTransactions) ->

                //Transactions dateWise
                item {
                    val dayIncome = dayTransactions.filter { it.type == "Income" && SimpleDateFormat("dd/MM/yyyy").format(it.time) == date }.sumOf { it.amount }
                    val dayExpense = dayTransactions.filter { it.type == "Expense" && SimpleDateFormat("dd/MM/yyyy").format(it.time) == date }.sumOf { it.amount }

                    //DayTransaction summary in Row: Date, Income, Expense
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceEvenly

                    ) {
                        Text(
                            text = "$date",
                            fontSize = 20.sp,
                        )
                        Text(
                            text = "+ ₹$dayIncome",
                            fontSize = 20.sp,
                            color = Color.Blue
                        )
                        Text(
                            text = "- ₹$dayExpense",
                            fontSize = 20.sp,
                            color = Color.Red
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }

                items(dayTransactions.size) { index ->
                    val transaction = dayTransactions[index]
                    val category = transaction.category
                    val note = transaction.note
                    val amount = transaction.amount
                    val type = transaction.type


                    //Transaction
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(NavigationRoutes.AddEdit(
                                    transactionId = transaction.id,
                                    transactionType = type,
                                    transactionCategory = category,
                                    transactionAmount = amount.toString(),
                                    transactionNote = note
                                ))
                            }
                        ,
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = "$category",
                            fontSize = 20.sp,
                        )
                        Text(
                            text = "$note",
                            fontSize = 20.sp,
                        )
                        Text(
                            text = if(type == "Income"){
                                "+ ₹$amount"
                            }
                            else{
                                "- ₹$amount"
                            },
                            fontSize = 20.sp,
                            color = if(type == "Income"){
                                Color.Blue
                            }
                            else{
                                Color.Red
                            }
                        )
                    }
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.dp,
                        color = Color.LightGray
                    )
                    Spacer(modifier = Modifier.height(15.dp))

                }
            }

        }
        Spacer(modifier = Modifier.height(30.dp))

    }


}