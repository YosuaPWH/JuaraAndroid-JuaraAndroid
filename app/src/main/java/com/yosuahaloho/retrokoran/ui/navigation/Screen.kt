package com.yosuahaloho.retrokoran.ui.navigation

/**
 * Created by Yosua on 11/05/2023
 */
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Saved : Screen("saved")
    object Detail : Screen("detail/{data}")
}
