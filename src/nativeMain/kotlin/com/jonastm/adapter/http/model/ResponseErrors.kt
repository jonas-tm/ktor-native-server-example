package com.jonastm.adapter.http.model

import kotlinx.serialization.Serializable

@Serializable
data class Error(val key: String, val msg: String)

fun internalError() = Error("internal", "internal server error")

fun notFoundError() = Error("not_found", "entity not found")

fun invalidBodyError() = Error("invalid_body", "incorrect body format")