package de.tonndorf_martini.jonas.model

import kotlinx.serialization.Serializable

@Serializable
data class TestDTO(
    val msg: String
)