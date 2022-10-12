import java.lang.management.ManagementFactory

val ktor_version = "2.1.2"
val kotlin_version = "1.7.20" // When updating also update kotlin plugins version
val kotlin_coroutine = "1.6.4"
val kotlin_datetime = "0.4.0"

val sqldelight_postgres_native_version = "0.0.2"
val sqldelight_version = "1.5.4"

plugins {
    kotlin("multiplatform") version "1.7.20"
    kotlin("plugin.serialization") version "1.7.20"

    id("app.cash.sqldelight") version "2.0.0-alpha04"
}

group = "me.jonastm"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
}

task<DefaultTask>("projectName") {
    println(project.name)
}

// Enable context-receivers (not working)
//tasks.withType<KotlinCompile>().all {
//    kotlinOptions {
//        freeCompilerArgs = listOf("-Xcontext-receivers")
//    }
//}

//kotlin.targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
//    binaries.all {
//        freeCompilerArgs += "-Xdisable-phases=EscapeAnalysis"
//    }
//}

sqldelight {
    database("NativePostgres") {
        dialect("app.softwork:postgres-native-sqldelight-dialect:$sqldelight_postgres_native_version")
        packageName = "de.tm.jonas.data"
        deriveSchemaFromMigrations = true
    }
    linkSqlite = false
}

kotlin {
    val os = ManagementFactory.getOperatingSystemMXBean()
    val isArm = os.arch == "aarch64"
    val isMacOS = os.name == "Mac OS X"
    val isLinux = os.name == "Linux"
    val nativeTarget = when {

        isMacOS && isArm -> macosArm64("native")
        isMacOS -> macosX64("native")

        isLinux && isArm -> linuxArm64("native")
        isLinux -> linuxX64("native")

        //hostOs.startsWith("Windows") -> mingwX64("native") // Not yet supported by kotlin/native ktor
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    nativeTarget.apply {
        binaries {
            executable {
                entryPoint = "de.tm.jonas.main"
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("app.softwork:postgres-native-sqldelight-driver:$sqldelight_postgres_native_version")
            }
        }

        val nativeMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:$kotlin_datetime")

                implementation("io.ktor:ktor-server-core:$ktor_version")
                implementation("io.ktor:ktor-server-cio:$ktor_version")

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlin_coroutine")

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
