package com.yosuahaloho.retrokoran.domain.repository

import com.yosuahaloho.retrokoran.domain.model.Article
import com.yosuahaloho.retrokoran.util.Result
import kotlinx.coroutines.flow.Flow

/**
 * Created by Yosua on 13/05/2023
 */
interface NewsRepository {

    suspend fun getHeadlineNews(country: String, pageSize: Int) : Flow<Result<List<Article>>>

    suspend fun getContent(link: String): String

    suspend fun addToBookmarkedNews(news: Article)

    suspend fun removeFromBookmarkedNews(news: Article)

    suspend fun isBookmarkedNews(title: String): Boolean

    suspend fun getBookmarkedNews(): Flow<Result<List<Article>>>

    suspend fun searchNews(sources: String, pageSize: Int, query: String): Flow<Result<List<Article>>>
}