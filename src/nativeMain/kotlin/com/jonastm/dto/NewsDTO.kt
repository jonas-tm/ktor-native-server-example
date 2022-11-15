package com.jonastm.dto

import kotlinx.serialization.Serializable

@Serializable
data class NewsDTO(
    val id: Long = 0,
    val title: String,
    val text: String,
)