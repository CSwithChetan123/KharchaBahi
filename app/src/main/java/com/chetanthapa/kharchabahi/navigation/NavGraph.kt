package com.chetanthapa.kharchabahi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.chetanthapa.kharchabahi.screens.AddEditScreen
import com.chetanthapa.kharchabahi.screens.BudgetScreen
import com.chetanthapa.kharchabahi.screens.MainScreen
import com.chetanthapa.kharchabahi.screens.StatsScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController,
        startDestination = NavigationRoutes.MainScreen
    ) {
        composable<NavigationRoutes.MainScreen> {
            MainScreen(navController)
        }
        composable<NavigationRoutes.Stats> {
            StatsScreen(navController)
        }
        composable<NavigationRoutes.Budget> {
            BudgetScreen(navController)
        }
        composable<NavigationRoutes.AddEdit> { backStackEntry ->
            val transactionData = backStackEntry.toRoute<NavigationRoutes.AddEdit>()
            AddEditScreen(navController, transactionData.transactionId, transactionData.transactionType, transactionData.transactionCategory, transactionData.transactionAmount, transactionData.transactionNote)
        }


    }


}