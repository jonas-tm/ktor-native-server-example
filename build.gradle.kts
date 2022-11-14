val ktor_version = "2.1.3"
val kotlin_version = "1.7.21" // When updating also update kotlin plugins version
val psql_driver_version = "0.0.4"
val kotlin_coroutine = "1.6.4"
val kotlinx_serialization_version = "1.4.0"

plugins {
    kotlin("multiplatform") version "1.7.21"
    kotlin("plugin.serialization") version "1.7.21"

    id("app.cash.sqldelight") version "2.0.0-alpha04"
}

group = "com.jonastm"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

sqldelight {
    database("NativePostgres") {
        dialect("app.softwork:postgres-native-sqldelight-dialect:$psql_driver_version")
        packageName = "com.jonastm.orm.sqldelight"
    }
    linkSqlite = false
}

kotlin {
    val hostOs = System.getProperty("os.name")
    val arm: Boolean = System.getProperty("os.arch") == "aarch64"
    val target = "native"
    val nativeTarget = when {
        hostOs == "Mac OS X" -> if (arm) macosArm64(target) else macosArm64(target)
        hostOs == "Linux" -> if (arm) linuxArm64(target) else linuxX64(target)
        else -> throw GradleException("Host OS is not supported in Kotlin/Native + Ktor.")
    }

    nativeTarget.apply {
        binaries {
            executable {
                entryPoint = "com.jonastm.main"
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            // SQLDelight ORM will be generated here
        }
        val nativeMain by getting {
            dependencies {
                implementation("io.ktor:ktor-server-core:$ktor_version")
                implementation("io.ktor:ktor-server-cio:$ktor_version")

                implementation("app.softwork:postgres-native-sqldelight-driver:$psql_driver_version")
                implementation("com.squareup.sqldelight:coroutines-extensions:1.5.3")

                implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlin_coroutine")

                // Fix to sync kotlinx serialization version
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$kotlinx_serialization_version") {
                    version { strictly(kotlinx_serialization_version) }
                }
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinx_serialization_version") {
                    version { strictly(kotlinx_serialization_version) }
                }

                implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
                implementation("io.ktor:ktor-server-status-pages:$ktor_version")
                implementation("io.ktor:ktor-server-call-id:$ktor_version")
            }
        }
        val nativeTest by getting {
            dependencies {
                implementation("io.ktor:ktor-server-test-host:$ktor_version")
                implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
                implementation("org.jetbrains.kotlin:kotlin-test:$kotlin_version")
            }
        }
    }
}
