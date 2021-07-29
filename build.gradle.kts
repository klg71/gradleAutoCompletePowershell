plugins {
    kotlin("jvm") version "1.4.21"

    // static code analysis
    id("io.gitlab.arturbosch.detekt") version "1.16.0"
    id("com.diffplug.spotless") version "5.6.1"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}
val ktLintVersion = "0.41.0"
spotless {
    kotlin {
        ktlint(ktLintVersion)
    }
}

project.group = "net.mayope"

repositories {
    jcenter()
    mavenCentral()
    maven {
        setUrl("https://repo.gradle.org/gradle/libs-releases-local/")
    }
}

dependencies {
    api(kotlin("stdlib"))
    api(kotlin("reflect"))
    implementation("org.gradle:gradle-tooling-api:7.0")
}

tasks.withType<Jar> {
    manifest {
        attributes(mapOf("Main-Class" to "net.mayope.gradlelisttasks.MainKt"))
    }
}

tasks {
    withType<io.gitlab.arturbosch.detekt.Detekt> {
        // Target version of the generated JVM bytecode. It is used for type resolution.
        this.jvmTarget = "1.8"
    }
}
tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class.java).all {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
