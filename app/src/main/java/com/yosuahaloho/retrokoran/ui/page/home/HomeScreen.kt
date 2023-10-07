package com.yosuahaloho.retrokoran.ui.page.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.yosuahaloho.retrokoran.domain.model.Article
import com.yosuahaloho.retrokoran.ui.component.EmptyAnimation
import com.yosuahaloho.retrokoran.ui.component.Header
import com.yosuahaloho.retrokoran.ui.component.LayoutItemShimmer
import com.yosuahaloho.retrokoran.ui.component.NewsItem
import com.yosuahaloho.retrokoran.ui.component.ShowToast
import com.yosuahaloho.retrokoran.ui.navigation.Screen
import com.yosuahaloho.retrokoran.util.UiState

/**
 * Created by Yosua on 11/05/2023
 */
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController,
    onClickSearch: (Boolean) -> Unit
) {
    Column(
        modifier = modifier
            .padding(10.dp)
            .fillMaxSize()
    ) {
        Header(
            viewModel = viewModel,
            navController = navController,
            onClickSearch = onClickSearch
        )

        viewModel.uiState.collectAsState(initial = UiState.Loading).value.let {
            when (it) {
                is UiState.Loading -> {
                    LayoutItemShimmer()
                }

                is UiState.Success -> {
                    if (it.data.isEmpty()) {
                        EmptyAnimation()
                    } else {
                        HomeContent(
                            news = it.data,
                            navController = navController,
                        )
                    }
                }

                is UiState.Error -> {
                    ShowToast(message = it.errorMessage)
                }
            }
        }
    }


}

@Composable
fun HomeContent(
    news: List<Article>,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    LazyColumn {
        items(news, key = { data -> data.title }) { article ->
            NewsItem(
                article = article,
                onClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        key = "article",
                        value = article
                    )
                    navController.navigate(route = Screen.Detail.route)
                }
            )
            Spacer(modifier = modifier.height(8.dp))
        }
    }
}

