plugins {
    `kotlin-dsl`
    id("com.diffplug.spotless") version "6.0.0"
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("com.diffplug.spotless:spotless-plugin-gradle:5.17.1")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.0-RC2")
}

spotless {
    kotlinGradle {
        ktlint()
    }
}
