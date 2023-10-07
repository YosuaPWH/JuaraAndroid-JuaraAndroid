package com.yosuahaloho.retrokoran.data.repository

import android.util.Log
import com.yosuahaloho.retrokoran.data.local.dao.BookmarkNewsDao
import com.yosuahaloho.retrokoran.data.remote.ApiService
import com.yosuahaloho.retrokoran.domain.model.Article
import com.yosuahaloho.retrokoran.domain.repository.NewsRepository
import com.yosuahaloho.retrokoran.util.Result
import com.yosuahaloho.retrokoran.util.parseDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

/**
 * Created by Yosua on 13/05/2023
 */
class NewsRepositoryImpl constructor(
    private val apiService: ApiService,
    private val dao: BookmarkNewsDao
) : NewsRepository {

    override suspend fun getContent(link: String): String = withContext(Dispatchers.IO) {
        val doc = Jsoup.connect(link).get()

        var detail = doc.getElementsByClass("showblog-body__content")
        if (detail.isEmpty()) {
            detail = doc.getElementsByClass("article-body__content")
        }
        val paragraph = detail.select("p")
        val detailText = StringBuilder()

        for (p in paragraph) {
            detailText.append(p.text()).append("\n").append("\n")
        }

        return@withContext detailText.toString()
    }

    override suspend fun getHeadlineNews(
        country: String,
        pageSize: Int
    ): Flow<Result<List<Article>>> = flow {
        try {
            apiService.getHeadlineNews(country, pageSize).let { response ->
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        val data = body.articles.distinctBy {
                            it.title
                        }.map {
                            val convertedDate = parseDate(
                                it.publishedAt,
                                "yyyy-MM-dd'T'HH:mm:ss'Z'",
                                "MMM dd yyyy HH:mm 'GMT'Z"
                            )
                            it.copy(publishedAt = convertedDate)
                        }
                        emit(Result.Success(data))
                    } else {
                        emit(Result.Failure("NewsRepo-getHeadlineNews: Response body is null"))
                    }
                } else {
                    emit(Result.Failure("NewsRepo-getHeadlineNews: Response error: ${response.errorBody()}"))
                }
            }
        } catch (e: Exception) {
            emit(Result.Failure("NewsRepo-getHeadlineNews-Excep: ${e.message}"))
        }
    }

    override suspend fun addToBookmarkedNews(news: Article) {
        try {
            dao.insert(news)
        } catch (e: Exception) {
            Log.e("Repo-AddToBookmarkedNews", e.message.toString())
        }
    }

    override suspend fun removeFromBookmarkedNews(news: Article) {
        try {
            dao.delete(news)
        } catch (e: Exception) {
            Log.e("Repo-RemoveFromBookmarkedNews", e.message.toString())
        }
    }

    override suspend fun isBookmarkedNews(title: String): Boolean {
        return try {
            dao.isBookmarked(title)
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getBookmarkedNews(): Flow<Result<List<Article>>> = flow {
        try {
            val data = dao.getAllBookmarkedNews()
            emit(Result.Success(data))
        } catch (e: Exception) {
            emit(Result.Failure("GetBookmarkedNews ${e.message}"))
        }
    }

    override suspend fun searchNews(
        sources: String,
        pageSize: Int,
        query: String
    ): Flow<Result<List<Article>>> = flow {
        try {
            apiService.searchNews(sources, pageSize, query).let { response ->
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        val data = body.articles.distinctBy {
                            it.title
                        }.map {
                            val convertedDate = parseDate(
                                it.publishedAt,
                                "yyyy-MM-dd'T'HH:mm:ss'Z'",
                                "MMM dd yyyy HH:mm 'GMT'Z"
                            )
                            it.copy(publishedAt = convertedDate)
                        }
                        emit(Result.Success(data))
                    } else {
                        emit(Result.Failure("NewsRepo-searchNews: Response body is null"))
                    }
                } else {
                    emit(Result.Failure("NewsRepo-searchNews: Response error: ${response.errorBody()}"))
                }
            }
        } catch (e: Exception) {
            emit(Result.Failure("NewsRepo-searchNews-Excep: ${e.message}"))
        }
    }

}