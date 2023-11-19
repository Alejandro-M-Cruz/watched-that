package com.example.watchedthat.unused

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.watchedthat.model.Genre

@Dao
interface GenreDao {
    @Query("SELECT * FROM genres")
    fun getAll(): List<Genre>

    @Query("SELECT * FROM genres WHERE id IN (:ids)")
    fun getByIds(ids: List<Int>)

    @Insert
    fun insert(genres: List<Genre>)
}