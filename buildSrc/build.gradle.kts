plugins {
    `kotlin-dsl`
    id("com.diffplug.spotless") version "6.1.0"
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("com.diffplug.spotless:spotless-plugin-gradle:6.0.5")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
}

spotless {
    kotlinGradle {
        ktlint()
    }
}
