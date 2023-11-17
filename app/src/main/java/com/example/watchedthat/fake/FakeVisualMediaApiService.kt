package com.example.watchedthat.fake

import com.example.watchedthat.model.VisualMedia
import com.example.watchedthat.network.PagedResponse
import com.example.watchedthat.network.VisualMediaApiService


class FakeVisualMediaApiService : VisualMediaApiService {
    override suspend fun search(query: String): PagedResponse<VisualMedia> {
        return PagedResponse(
            page = 1,
            totalPages = 1,
            totalResults = 1,
            results = FakeDataSource.visualMediaList
        )
    }

}