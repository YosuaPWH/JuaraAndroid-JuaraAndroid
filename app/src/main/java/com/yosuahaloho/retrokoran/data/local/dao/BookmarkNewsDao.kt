package com.yosuahaloho.retrokoran.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yosuahaloho.retrokoran.domain.model.Article

/**
 * Created by Yosua on 22/05/2023
 */
@Dao
interface BookmarkNewsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(news: Article)

    @Delete
    suspend fun delete(news: Article)

    @Query("SELECT EXISTS(SELECT * FROM bookmark WHERE title = :title)")
    suspend fun isBookmarked(title: String): Boolean

    @Query("SELECT * FROM bookmark ORDER BY title ASC")
    suspend fun getAllBookmarkedNews(): List<Article>
}