pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id.startsWith("com.google.cloud.tools.appengine")) {
                useModule("com.google.cloud.tools:appengine-gradle-plugin:${requested.version}")
            }
        }
    }
}

rootProject.name = "ktor-codesnippets"

fun module(group: String, name: String) {
    include(name)
    project(":$name").projectDir = file("$group/$name")
}

// ---------------------------

module("snippets", "embedded-server-native")

