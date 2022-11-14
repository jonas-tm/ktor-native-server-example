package com.jonastm.adapter.persistence.database

import app.softwork.sqldelight.postgresdriver.PostgresNativeDriver
import com.jonastm.Env
import com.jonastm.orm.sqldelight.NativePostgres

typealias DB = NativePostgres

fun initPostgresConn(config: Env.Postgres): DB {

    val driver = PostgresNativeDriver(
        host = config.host,
        port = config.port,
        user = config.user,
        database = config.databaseName,
        password = config.password
    )

    val db = NativePostgres(driver)
    NativePostgres.Schema.create(driver)
//    NativePostgres.Schema.migrate(driver, 1, 2)

    return db
}