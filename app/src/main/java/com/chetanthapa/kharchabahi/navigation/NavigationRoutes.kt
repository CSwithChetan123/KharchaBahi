package com.chetanthapa.kharchabahi.navigation

import kotlinx.serialization.Serializable


@Serializable
sealed class NavigationRoutes {
    @Serializable
    object MainScreen : NavigationRoutes()
    @Serializable
    object Stats : NavigationRoutes()
    @Serializable
    object Budget : NavigationRoutes()
    @Serializable
    data class AddEdit(
        val transactionId: Int = -1,
        val transactionType: String = "",
        val transactionCategory: String = "",
        val transactionAmount: String = "0.0",
        val transactionNote: String = ""
    ) : NavigationRoutes()

}