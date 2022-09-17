package de.tm.jonas.logger

import kotlinx.serialization.json.Json

val JSON = Json {
    allowStructuredMapKeys = true
    isLenient = true
    prettyPrint = true
}
