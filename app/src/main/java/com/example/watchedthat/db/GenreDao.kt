package com.example.watchedthat.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.watchedthat.model.genre.Genre

@Dao
interface GenreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(genres: List<Genre>)

    @Query("SELECT * FROM genres ORDER BY name ASC")
    suspend fun getAll(): List<Genre>
}