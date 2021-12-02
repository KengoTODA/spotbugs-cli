plugins {
    `kotlin-dsl`
    id("com.diffplug.spotless") version "6.0.1"
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("com.diffplug.spotless:spotless-plugin-gradle:6.0.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.0")
}

spotless {
    kotlinGradle {
        ktlint()
    }
}
