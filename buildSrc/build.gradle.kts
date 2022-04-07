plugins {
    `kotlin-dsl`
    id("com.diffplug.spotless") version "6.4.2"
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("com.diffplug.spotless:spotless-plugin-gradle:6.4.2")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.20")
}

spotless {
    kotlinGradle {
        ktlint()
    }
}
