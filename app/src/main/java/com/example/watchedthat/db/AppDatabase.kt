package com.example.watchedthat.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.watchedthat.model.Genre
import com.example.watchedthat.model.SavedVisualMedia
import com.example.watchedthat.unused.GenreDao

@Database(entities = [SavedVisualMedia::class, Genre::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun savedVisualMediaDao(): SavedVisualMediaDao
    abstract fun genreDao(): GenreDao

    companion object {
        private var Instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "watched_that_app_database"
                ).fallbackToDestructiveMigration().build().also { Instance = it }
            }
        }
    }
}