package com.example.watchedthat.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.watchedthat.fake.FakeDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GenreDaoTest {
    private lateinit var db: AppDatabase
    private lateinit var dao: GenreDao

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.genreDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAll_insertsAllGivenGenres() = runTest {
        val genres = FakeDataSource.genres
        dao.insertAll(genres)
        val storedGenres = dao.getAll().first()
        assert(storedGenres.containsAll(genres) && storedGenres.size == genres.size)
    }

    @Test
    fun getAll_returnsAllGenresOrderedByName() = runTest {
        val genres = FakeDataSource.genres
        dao.insertAll(genres)
        assert(dao.getAll().first() == genres.sortedBy { it.name })
    }
}