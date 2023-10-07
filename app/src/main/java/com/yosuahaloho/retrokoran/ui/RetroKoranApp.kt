package com.yosuahaloho.retrokoran.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yosuahaloho.retrokoran.domain.model.Article
import com.yosuahaloho.retrokoran.ui.component.BottomBar
import com.yosuahaloho.retrokoran.ui.navigation.Screen
import com.yosuahaloho.retrokoran.ui.page.bookmark.BookmarkScreen
import com.yosuahaloho.retrokoran.ui.page.detail_news.DetailNewsScreen
import com.yosuahaloho.retrokoran.ui.page.home.HomeScreen
import com.yosuahaloho.retrokoran.ui.theme.RetroKoranTheme

/**
 * Created by Yosua on 11/05/2023
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RetroKoranApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var displayBottomNavigation by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            if (currentRoute == Screen.Home.route ||
                currentRoute == Screen.Saved.route
            ) {
                BottomBar(navController = navController)
            }
        },
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = modifier.padding(paddingValues),
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navController = navController,
                    onClickSearch = {
                        displayBottomNavigation = it
                    },
                )
            }
            composable(Screen.Saved.route) {
                BookmarkScreen(navController = navController)
            }
            composable(
                route = Screen.Detail.route,
            ) {
                val result =
                    navController.previousBackStackEntry?.savedStateHandle?.get<Article>("article")
                if (result != null) {
                    DetailNewsScreen(
                        article = result,
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun RetroKoranPreview() {
    RetroKoranTheme {
        RetroKoranApp()
    }
}
