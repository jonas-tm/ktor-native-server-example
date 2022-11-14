package com.jonastm.adapter.http.model

import kotlinx.serialization.Serializable

@Serializable
abstract class Error(val key: String, val msg: String)

@Serializable
class InternalError: Error("internal", "internal server error")

@Serializable
class NotFoundError: Error("not_found", "entity not found")

@Serializable
class InvalidBodyError: Error("invalid_body", "incorrect body format")