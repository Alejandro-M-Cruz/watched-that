package com.example.watchedthat.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.watchedthat.model.visualmedia.SavedVisualMedia

@Database(entities = [SavedVisualMedia::class], version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun savedVisualMediaDao(): SavedVisualMediaDao

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