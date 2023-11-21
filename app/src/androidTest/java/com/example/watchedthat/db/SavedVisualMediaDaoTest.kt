package com.example.watchedthat.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.watchedthat.fake.FakeDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SavedVisualMediaDaoTest {
    private lateinit var db: AppDatabase
    private lateinit var dao: SavedVisualMediaDao
    private val savedVisualMedia1 = FakeDataSource.savedVisualMedia1
    private val savedVisualMedia2 = FakeDataSource.savedVisualMedia2

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.savedVisualMediaDao()
    }
    @After
    fun closeDb() {
        db.close()
    }

    private suspend fun addOneItemToWishList() {
        dao.addToWishList(savedVisualMedia1)
    }

    private suspend fun addOneItemToWatched() {
        dao.markAsWatched(savedVisualMedia1)
    }

    private suspend fun addTwoItemsToWishList() {
        dao.addToWishList(savedVisualMedia1)
        dao.addToWishList(savedVisualMedia2)
    }

    private suspend fun addTwoItemsToWatched() {
        dao.markAsWatched(savedVisualMedia1)
        dao.markAsWatched(savedVisualMedia2)
    }

    @Test
    fun getWishList_returnsAllItemsAddedToWishlist() = runBlocking {
        addTwoItemsToWishList()
        val wishlist = dao.getWishlist().first()
        assert(wishlist.size == 2)
        assert(wishlist.contains(savedVisualMedia1))
        assert(wishlist.contains(savedVisualMedia2))
    }

    @Test
    fun getWatched_returnsAllItemsMarkedAsWatched() = runBlocking {
        addTwoItemsToWatched()
        val watched = dao.getWatched().first()
        assert(watched.size == 2)
        assert(watched.contains(savedVisualMedia1))
        assert(watched.contains(savedVisualMedia2))
    }

    @Test
    fun addToWishList_addsItemToWishlistAndRemovesItFromWatched() = runBlocking {
        addOneItemToWatched()
        dao.addToWishList(savedVisualMedia1)
        val wishlist = dao.getWishlist().first()
        val watched = dao.getWatched().first()
        assertEquals(wishlist, listOf(savedVisualMedia1))
        assert(watched.isEmpty())
    }

    @Test
    fun markAsWatched_marksItemAsWatchedAndRemovesItFromWishlist() = runBlocking {
        addOneItemToWishList()
        dao.markAsWatched(savedVisualMedia1)
        val wishlist = dao.getWishlist().first()
        val watched = dao.getWatched().first()
        assertEquals(watched, listOf(savedVisualMedia1))
        assert(wishlist.isEmpty())
    }

    @Test
    fun removeFromWishlist_removesItemFromWishlist() = runBlocking {
        addOneItemToWishList()
        dao.removeFromWishlist(savedVisualMedia1)
        val wishlist = dao.getWishlist().first()
        assert(wishlist.isEmpty())
    }

    @Test
    fun unmarkAsWatched_unmarksItemAsWatched() = runBlocking {
        addOneItemToWatched()
        dao.unmarkAsWatched(savedVisualMedia1)
        val watched = dao.getWatched().first()
        assert(watched.isEmpty())
    }
}