package com.yosuahaloho.retrokoran.data.remote

import com.yosuahaloho.retrokoran.data.remote.response.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Yosua on 13/05/2023
 */
interface ApiService {

    @GET("everything")
    suspend fun getHeadlineNews(
        @Query("sources") sources: String,
        @Query("pageSize") pageSize: Int
    ): Response<NewsResponse>

    @GET("everything")
    suspend fun searchNews(
        @Query("sources") sources: String,
        @Query("pageSize") pageSize: Int,
        @Query("q") query: String
    ): Response<NewsResponse>
}