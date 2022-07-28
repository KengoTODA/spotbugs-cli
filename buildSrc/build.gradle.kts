plugins {
    `kotlin-dsl`
    id("com.diffplug.spotless") version "6.8.0"
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("com.diffplug.spotless:spotless-plugin-gradle:6.9.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
}

spotless {
    kotlinGradle {
        ktlint()
    }
}
