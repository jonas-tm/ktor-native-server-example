package com.jonastm.service.impl

import com.jonastm.service.NewsService
import com.jonastm.adapter.persistence.database.DB
import com.jonastm.dto.NewsDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsServiceImpl(val db: DB) : NewsService {

    val mapper =  { id: Long, title: String, text: String -> NewsDTO(id, title, text) }

    override suspend fun getAllNewsEntries(): List<NewsDTO> = withContext(Dispatchers.Default) {
        db.newsQueries.findAll(mapper).executeAsList()
    }

    override suspend fun getNewsEntry(id: Long): NewsDTO?  = withContext(Dispatchers.Default)  {
        db.newsQueries.selectById(id, mapper).executeAsOneOrNull()
    }

    override suspend fun addNewsEntry(newsEntry: NewsDTO): NewsDTO  = withContext(Dispatchers.Default)  {
        val id = db.newsQueries.insertAndGetId(newsEntry.title, newsEntry.text).executeAsOne()
        NewsDTO(id, newsEntry.title, newsEntry.text)
    }

}