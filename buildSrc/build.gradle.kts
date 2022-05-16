plugins {
    `kotlin-dsl`
    id("com.diffplug.spotless") version "6.6.0"
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("com.diffplug.spotless:spotless-plugin-gradle:6.6.1")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
}

spotless {
    kotlinGradle {
        ktlint()
    }
}
