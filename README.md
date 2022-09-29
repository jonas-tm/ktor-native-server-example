# Ktor Native Server Example

This repository contains a sample ktor 2.0 server program that runs only with Kotlin/Native.

The server includes the following functionalities:
- Sample REST API with KotlinX JSON Serialization
- Global error handling middleware
- Sample Application Plugin (Middleware) for request logging
- Coroutine support and sample endpoint

The ktor server is running with CIO Engine (Coroutine-based I/O) which is the only on supported by Kotlin/Native (Netty, Jetty and Tomcat are JVM based).
More general information can be found here: https://ktor.io/docs/native-server.html

## Supported Platforms
- Linux (x64, Arm64)
- MacOS (x64, Arm64)

## How to run
Currently, development experience is not very good. The program has to be started by hand.

1. Debug `./gradlew runDebugExecutable`
2. Tests `./gradlew allTests`

## Known Problems
- On MacOS server is not always stopped after Gradle debug task stopped
  - Kill process with `kill $(pgrep $(./gradlew projectName -q))`
- Problems with tests in IntelliJ: _Cannot access class 'io.ktor...'. Check your module classpath for missing or conflicting dependencies_
  - [Open bug](https://youtrack.jetbrains.com/issue/KT-52216/HMPP-KTOR-False-positive-TYPEMISMATCH-with-Throwable-descendant#focus=Comments-27-6032809.0-0) can be overcome by adding following to `gradle.properties`
    - ```kotlin.mpp.hierarchicalStructureSupport=false```

## Planned Features
- Database connection via Exposed [once supported](https://github.com/JetBrains/Exposed/blob/master/docs/ROADMAP.md)