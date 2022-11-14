package com.jonastm.service

import com.jonastm.dto.NewsDTO

interface NewsService {
    suspend fun getAllNewsEntries(): List<NewsDTO>
    suspend fun getNewsEntry(id: Long): NewsDTO?
    suspend fun addNewsEntry(newsEntry: NewsDTO): NewsDTO
}
