package com.yosuahaloho.retrokoran.ui.page.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yosuahaloho.retrokoran.domain.model.Article
import com.yosuahaloho.retrokoran.domain.repository.NewsRepository
import com.yosuahaloho.retrokoran.util.Result
import com.yosuahaloho.retrokoran.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Yosua on 13/05/2023
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsRepo: NewsRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<Article>>> =
        MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<List<Article>>> get() = _uiState

    private val _searchResult: MutableStateFlow<UiState<List<Article>>> = MutableStateFlow(UiState.Loading)

    val searchResult: StateFlow<UiState<List<Article>>> get() = _searchResult

    var blockLoading = false
    init {
        getHeadlineNews()
    }

    private fun getHeadlineNews(sources: String = "nbc-news", pageSize: Int = 30) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            newsRepo.getHeadlineNews(sources, pageSize).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.value = UiState.Success(result.value)
                    }

                    is Result.Failure -> {
                        _uiState.value = UiState.Error(result.message)
                    }
                }
            }

        }
    }

    fun searchNews(sources: String = "nbc-news", pageSize: Int = 30, query: String) {
        blockLoading = true
        _searchResult.value = UiState.Loading
        viewModelScope.launch {
            newsRepo.searchNews(sources, pageSize, query).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _searchResult.value = UiState.Success(result.value)
                    }

                    is Result.Failure -> {
                        _searchResult.value = UiState.Error(result.message)
                    }
                }
            }
        }
    }

}