package com.yosuahaloho.retrokoran.ui.page.bookmark

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.yosuahaloho.retrokoran.domain.model.Article
import com.yosuahaloho.retrokoran.ui.component.EmptyAnimation
import com.yosuahaloho.retrokoran.ui.component.LayoutItemShimmer
import com.yosuahaloho.retrokoran.ui.component.NewsItem
import com.yosuahaloho.retrokoran.ui.component.ShowToast
import com.yosuahaloho.retrokoran.ui.navigation.Screen
import com.yosuahaloho.retrokoran.ui.theme.DMSerif
import com.yosuahaloho.retrokoran.util.UiState

/**
 * Created by Yosua on 11/05/2023
 */
@Composable
fun BookmarkScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: BookmarkViewModel = hiltViewModel()
) {
    viewModel.getBookmarkedNews()
    Box(
        modifier = modifier
            .padding(10.dp)
            .fillMaxSize()
    ) {
        Column {
            Text(
                text = "RetroKoran",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 32.sp,
                fontFamily = DMSerif
            )
            Text(
                text = "Your Bookmark",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                fontFamily = DMSerif,
                modifier = modifier.testTag("bookmark")
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
                            BookmarkContent(
                                bookmarkNews = it.data,
                                navController = navController
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

}

@Composable
fun BookmarkContent(
    bookmarkNews: List<Article>,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    LazyColumn {
        items(bookmarkNews, key = { it.title }) { article ->
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