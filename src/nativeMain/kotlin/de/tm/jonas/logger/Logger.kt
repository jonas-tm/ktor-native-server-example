package de.tm.jonas.logger

import kotlinx.serialization.json.Json

val JSON = Json {
    allowStructuredMapKeys = true
    isLenient = true
    prettyPrint = true
}

interface LoggingContext {
    fun log(msg: String)
}

class BasicLogging() : LoggingContext {

    override fun log(msg: String) {
        println(msg)
    }
}
