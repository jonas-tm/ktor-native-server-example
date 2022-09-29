package de.tm.jonas.model

import kotlinx.serialization.Serializable

@Serializable
data class ErrorDTO(
    val msg: String,
    val fields: Map<String, String> = mapOf()
)