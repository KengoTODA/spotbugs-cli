import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile

plugins {
    `jacoco`
    id("com.diffplug.spotless")
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.kapt")
}

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain {
        (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of("17"))
    }
}

tasks.withType<KotlinJvmCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "11"
    }
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

spotless {
    kotlin {
        ktlint()
        licenseHeader("/* Copyright (C) \$YEAR Kengo TODA */")
    }
    kotlinGradle {
        ktlint()
    }
}
