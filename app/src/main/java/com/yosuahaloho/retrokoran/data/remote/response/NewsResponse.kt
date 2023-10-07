package com.yosuahaloho.retrokoran.data.remote.response

import com.yosuahaloho.retrokoran.domain.model.Article

/**
 * Created by Yosua on 13/05/2023
 */
data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)
