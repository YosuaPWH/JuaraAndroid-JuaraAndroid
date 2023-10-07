package com.yosuahaloho.retrokoran.ui.page.detail_news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yosuahaloho.retrokoran.domain.model.Article
import com.yosuahaloho.retrokoran.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Yosua on 22/05/2023
 */
@HiltViewModel
class DetailNewsViewModel @Inject constructor(
    private val newsRepo: NewsRepository
) : ViewModel() {

    private val _content: MutableStateFlow<String> = MutableStateFlow("")

    val content: StateFlow<String> get() = _content

    private val _isBookmarkedNews: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val isBookmarkedNews: StateFlow<Boolean> get() = _isBookmarkedNews

    fun getContent(link: String) {
        viewModelScope.launch {
            _content.value = newsRepo.getContent(link)
        }
    }

    fun checkIsBookmarkedNews(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isBookmarkedNews.value = newsRepo.isBookmarkedNews(title)
        }
    }

    fun changeBookmarkStatus(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isBookmarkedNews.value) {
                newsRepo.removeFromBookmarkedNews(article)
            } else {
                newsRepo.addToBookmarkedNews(article)
            }
            _isBookmarkedNews.value = !isBookmarkedNews.value
        }
    }
}