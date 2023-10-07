package com.yosuahaloho.retrokoran.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.yosuahaloho.retrokoran.ui.navigation.Screen
import com.yosuahaloho.retrokoran.ui.page.home.HomeViewModel
import com.yosuahaloho.retrokoran.util.UiState

/**
 * Created by Yosua on 23/05/2023
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchBarItem(
    searchFocus: FocusRequester,
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier,
    active: Boolean,
    navController: NavHostController,
    onClickSearch: (Boolean) -> Unit,
    onActive: (Boolean) -> Unit,
) {
    var text by remember { mutableStateOf("") }

    LaunchedEffect(active) {
        if (active) {
            searchFocus.requestFocus()
        }
    }

    SearchBar(
        modifier = (if (active) modifier.fillMaxSize() else modifier.size(0.dp))
            .onKeyEvent { keyEvent ->
                if (keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.Back) {
                    onClickSearch(false)
                    onActive(false)
                }
                true
            }
            .focusRequester(searchFocus)
            .testTag("SearchBar"),
        query = text,
        onQueryChange = { query ->
            text = query
        },
        onSearch = { query ->
            onActive(true)
            viewModel.searchNews(query = query)
        },
        active = active,
        onActiveChange = { isActive ->
            onActive(isActive)
        },
        placeholder = {
            Text(text = "Search News...")
        },
        leadingIcon = {
            IconButton(onClick = {
                onActive(false)
                onClickSearch(false)
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                )
            }
        },
        trailingIcon = {
            IconButton(onClick = {
                text = ""
            }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            }
        },
        colors = SearchBarDefaults.colors(
            inputFieldColors = TextFieldDefaults.colors(
                cursorColor = Color.Black,
            ),
        ),
    ) {
        viewModel.searchResult.collectAsState().value.let { state ->
            when (state) {
                is UiState.Loading -> {
                    if (viewModel.blockLoading) {
                        LayoutItemShimmer()
                    }
                }

                is UiState.Success -> {
                    if (state.data.isEmpty()) {
                        EmptyAnimation()
                    } else {
                        LazyColumn {
                            items(state.data, key = { data -> data.title }) { article ->
                                NewsItem(
                                    article = article,
                                    onClick = {
                                        navController.currentBackStackEntry?.savedStateHandle?.set(
                                            key = "article",
                                            value = article,
                                        )
                                        navController.navigate(route = Screen.Detail.route)
                                    },
                                )
                                Spacer(modifier = modifier.height(8.dp))
                            }
                        }
                    }
                }

                is UiState.Error -> {
                    ShowToast(message = state.errorMessage)
                }
            }
        }
    }
}
