plugins {
    `kotlin-dsl`
    id("com.diffplug.spotless") version "6.4.2"
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("com.diffplug.spotless:spotless-plugin-gradle:6.5.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
}

spotless {
    kotlinGradle {
        ktlint()
    }
}
