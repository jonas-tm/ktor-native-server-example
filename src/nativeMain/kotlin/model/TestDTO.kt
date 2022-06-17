package model

import kotlinx.serialization.Serializable

@Serializable
data class TestDTO(
    val msg: String
)