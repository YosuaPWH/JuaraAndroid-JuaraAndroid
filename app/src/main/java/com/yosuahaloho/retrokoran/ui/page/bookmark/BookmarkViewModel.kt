package com.yosuahaloho.retrokoran.ui.page.bookmark

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
 * Created by Yosua on 22/05/2023
 */
@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val newsRepo: NewsRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<Article>>> =
        MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<List<Article>>> get() = _uiState

    init {
        getBookmarkedNews()
    }

    fun getBookmarkedNews() {
        viewModelScope.launch {
            newsRepo.getBookmarkedNews().collect { result ->
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
}