package com.jonastm

import com.jonastm.adapter.http.startServer
import com.jonastm.adapter.persistence.database.initPostgresConn
import com.jonastm.service.impl.NewsServiceImpl


fun main() {
    val env = Env()
    val db = initPostgresConn(env.postgres)
    val newsService = NewsServiceImpl(db);
    startServer(newsService, env.http)
}

