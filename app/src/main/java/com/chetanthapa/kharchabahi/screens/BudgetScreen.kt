package com.chetanthapa.kharchabahi.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.chetanthapa.kharchabahi.navigation.NavigationRoutes
import com.chetanthapa.kharchabahi.viewModel.TransactionViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun BudgetScreen(navController: NavHostController) {


    val viewModel: TransactionViewModel = viewModel()
    val transactions by viewModel.allTransactions.collectAsState(initial = emptyList())

    //For saving budget
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences("budget", Context.MODE_PRIVATE)
    var totalBudget by remember {
        mutableStateOf(sharedPref.getString("monthly_budget", "") ?: "")
    }
    var isSaveBtnClicked by remember { mutableStateOf(false) }

    val totalSpent = viewModel.totalExpense(transactions)
    val goal = totalBudget.toDoubleOrNull() ?: 0.0
    val totalRemaining = (goal - totalSpent)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, start = 40.dp, end = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            val monthName = SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(Date())
            Text(
                text = "$monthName Budget",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f),
                value = totalBudget,
                onValueChange = {
                    totalBudget = it
                },
                label = { Text(text = "Total Budget") }
            )
            Spacer(modifier = Modifier.width(20.dp))

            //Save Btn
            Button(
                onClick = {
                    if(goal > 0){
                        isSaveBtnClicked = true
                        val sharedPref = context.getSharedPreferences("budget", Context.MODE_PRIVATE)
                        sharedPref.edit().putString("monthly_budget", totalBudget).apply()

                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if(goal > 0) Color.Blue else Color.Gray,

                    ),
                content = {
                    Text(text = "Save")
                }

            )

        }
        Spacer(modifier = Modifier.height(50.dp))

        //Total Budget
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ){
            Text(
                text = "Total Budget",
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.width(70.dp))
            Text(
                text = "₹$totalBudget",
                fontSize = 20.sp
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        //Spent
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ){
            Text(
                text = "Spent",
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.width(130.dp))
            Text(
                text = "₹$totalSpent",
                fontSize = 20.sp
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        //Remaining
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ){
            Text(
                text = "Remaining",
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.width(90.dp))
            Text(
                text = "₹$totalRemaining",
                fontSize = 20.sp
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val spentPercentage = ((totalSpent / goal) * 100).toInt()

            LinearProgressIndicator(
                progress = if(goal > 0) (totalSpent / goal).toFloat() else 0f
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "${if(goal > 0) spentPercentage else 0}%",
                fontSize = 20.sp
            )

        }

    }

}