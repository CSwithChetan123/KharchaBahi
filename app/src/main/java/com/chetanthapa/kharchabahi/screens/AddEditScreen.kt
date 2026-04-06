package com.chetanthapa.kharchabahi.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.chetanthapa.kharchabahi.R

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chetanthapa.kharchabahi.data.Transaction
import com.chetanthapa.kharchabahi.viewModel.TransactionViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AddEditScreen(
    navController: NavHostController,
    transactionId: Int = -1,
    transactionType: String = "",
    transactionCategory: String = "",
    transactionAmount: String = "",
    transactionNote: String = ""
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        val incomeCategories = listOf("💰 Salary", "🎁 Bonus", "🎀 Allowance", "💵 Other")
        val expenseCategories = listOf("🍔 Food", "🚕 Transport", "🛍️ Shopping", "💊 Health", "📚 Education", "🎭 Entertainment", "💡 Bills", "🏠 Rent", "📦 Other")

        var amount by remember { mutableStateOf(transactionAmount) }
        var notes by remember { mutableStateOf(transactionNote) }

        var isIncomeSelected by remember { mutableStateOf(transactionType == "Income") }
        var isExpenseSelected by remember { mutableStateOf(transactionType == "Expense") }

        var selectedType by remember { mutableStateOf(transactionType) }
        var selectedCategory by remember { mutableStateOf(transactionCategory) }


        val categories = if (isIncomeSelected) incomeCategories else expenseCategories
        var showCategoryPicker by remember { mutableStateOf(false) }

        val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        val currentTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())

        val viewModel: TransactionViewModel = viewModel()


        if (showCategoryPicker) {
            AlertDialog(
                onDismissRequest = { showCategoryPicker = false },
                confirmButton = {},
                text = {
                    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                        items(categories.size) { index ->
                            Card(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .fillMaxWidth(),
                            ) {
                                Text(
                                    text = categories[index],
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .clickable {
                                            selectedCategory = categories[index]
                                            showCategoryPicker = false
                                        }

                                )
                            }
                        }
                    }
                }
            )
        }





        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp, start = 20.dp, end = 20.dp),
        ) {

            // Back and title in top Row
            Row(

            ) {
                // Back Image
                Image(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                    contentDescription = "Back Arrow",
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    }

                )
                Spacer(modifier = Modifier.width(20.dp))

                //Screen Title
                Text(
                    text = "Add/Edit Screen",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

            }
            Spacer(modifier = Modifier.height(20.dp))

            // Income and Expense buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Income Button
                Button(
                    onClick = {
                        isIncomeSelected = true
                        isExpenseSelected = false
                        selectedType = "Income"
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if(isIncomeSelected) Color.Blue else Color.Gray
                    ),
                    content = {
                        Text(text = "Income")
                    }
                )

                // Expense Button
                Button(
                    onClick = {
                        isIncomeSelected = false
                        isExpenseSelected = true
                        selectedType = "Expense"


                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if(isExpenseSelected) Color.Red else Color.Gray
                    ),
                    content = {
                        Text(text = "Expense")
                    }
                )
            }
            Spacer(modifier = Modifier.height(40.dp))

            //Date and time
            Row(
                modifier = Modifier
                    .padding(start = 10.dp)
            ) {
                // Date
                Text(
                    text = "Date",
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(30.dp))
                Text(
                    text = "$currentDate",
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(10.dp))

                // Time
                Text(
                    text = "$currentTime",
                    fontSize = 20.sp
                )


            }
            Spacer(modifier = Modifier.height(20.dp))

            // Amount
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = amount,
                onValueChange = {
                    amount = it

                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                label = { Text(text = "Amount ₹") }

            )
            Spacer(modifier = Modifier.height(20.dp))

            //
            Box{
                //Category
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = selectedCategory,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(text = "Category") },

                    )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable{
                            showCategoryPicker = true
                        }
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            //Note
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                value = notes,
                onValueChange = {
                    notes = it
                },
                label = { Text(text = "Note") }

            )
            Spacer(modifier = Modifier.height(30.dp))

            //Save Button
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    if(amount.isNotEmpty() && selectedType.isNotEmpty() && selectedCategory.isNotEmpty()){
                        if(transactionId == -1){
                            viewModel.insertTransaction(
                                Transaction(
                                    time = System.currentTimeMillis(),
                                    amount = amount.toDouble(),
                                    type = selectedType,
                                    category = selectedCategory,
                                    note = notes
                                )
                            )
                        }
                        else{
                            viewModel.updateTransaction(
                                Transaction(
                                    /*
                                    saved parameter
                                    transactionType: String,
                                    transactionCategory: String,
                                    transactionAmount: Double,
                                    transactionNote: String
                                     */
                                    id = transactionId,
                                    time = System.currentTimeMillis(),
                                    amount = amount.toDouble(),
                                    type = selectedType,
                                    category = selectedCategory,
                                    note = notes

                                )
                            )
                        }
                    }
                    navController.popBackStack()
                },
                content = {
                    Text(text = "Save")
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            if(transactionId != -1){
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                    ,
                    onClick = {
                        viewModel.deleteTransaction(
                            Transaction(
                                id = transactionId,
                                time = System.currentTimeMillis(),
                                amount = transactionAmount.toDouble(),
                                type = transactionType,
                                category = transactionCategory,
                                note = transactionNote

                            )
                        )
                        navController.popBackStack()
                    },
                    content = {
                        Text(text = "Delete")
                    }
                )
            }
        }
    }

}

