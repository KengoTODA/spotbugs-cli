plugins {
    `kotlin-dsl`
    id("com.diffplug.spotless") version "6.10.0"
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("com.diffplug.spotless:spotless-plugin-gradle:6.7.2")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.20")
}

spotless {
    kotlinGradle {
        ktlint()
    }
}
