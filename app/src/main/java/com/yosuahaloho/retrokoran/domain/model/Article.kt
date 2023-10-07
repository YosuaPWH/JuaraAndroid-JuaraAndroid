package com.yosuahaloho.retrokoran.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Created by Yosua on 13/05/2023
 */
@Parcelize
@Entity(tableName = "bookmark")
data class Article(
    @PrimaryKey(autoGenerate = false)
    val title: String,
    val author: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
): Parcelable