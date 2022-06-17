# Ktor Native Server Example

This repository contains a sample ktor 2.0 server program that runs only with Kotlin/Native.

The server includes the following functionalities:
- Sample REST API with KotlinX JSON Serialization
- Sample Application Plugin (Middleware) for request logging
- Uses the experimental new memory model of Kotlin/Native (can be disabled in the `gradle.properties`).

The ktor server is running with CIO Engine (Coroutine-based I/O) which is the only on supported by Kotlin/Native (Netty, Jetty and Tomcat are JVM based).
More general information can be found here: https://ktor.io/docs/native-server.html

## Supported Platforms
- Linux
- MacOS

## How to run
Currently, development experience is not very good. The program has to be started by hand.

1. Build the program `./gradlew  :build`
2. Execute program by hand via `./build/bin/native/debugExecutable/ktor-native-server-example.kexe` (alternatively `releaseExecutable`)
3. Server should now be running under https://localhost:8080

## Planned Features
- Database connection via Exposed [once supported](https://github.com/JetBrains/Exposed/blob/master/docs/ROADMAP.md)