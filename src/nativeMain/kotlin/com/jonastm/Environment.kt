package com.jonastm

import kotlinx.cinterop.toKString
import platform.posix.getenv

// Defaults
private const val PORT: Int = 8080
private const val POSTGRES_HOST: String = "localhost"
private const val POSTGRES_PORT: Int = 5432
private const val POSTGRES_USER: String = "postgres"
private const val POSTGRES_PW: String = "postgres"
private const val POSTGRES_DB_NAME: String = "postgres"

data class Env(
    val postgres: Postgres = Postgres(),
    val http: Http = Http(),
) {
    data class Http(
        val host: String = getenv("HOST")?.toKString() ?: "0.0.0.0",
        val port: Int = getenv("SERVER_PORT")?.toKString()?.toIntOrNull() ?: PORT,
    )

    data class Postgres(
        val host: String = getenv("POSTGRES_HOST")?.toKString() ?: POSTGRES_HOST,
        val port: Int = getenv("POSTGRES_PORT")?.toKString()?.toIntOrNull() ?: POSTGRES_PORT,
        val user: String = getenv("POSTGRES_USERNAME")?.toKString() ?: POSTGRES_USER,
        val databaseName: String = getenv("POSTGRES_DB_NAME")?.toKString() ?: POSTGRES_DB_NAME,
        val password: String = getenv("POSTGRES_PASSWORD")?.toKString() ?: POSTGRES_PW,
    )
}