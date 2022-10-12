package de.tm.jonas.repository

import app.softwork.sqldelight.postgresdriver.PostgresNativeDriver


fun createDriver() {
    val driver = PostgresNativeDriver(
        host = "localhost",
        port = 5432,
        user = "postgres",
        database = "postgres",
        password = "password"
    )


//    val queries = NativePostgr
}