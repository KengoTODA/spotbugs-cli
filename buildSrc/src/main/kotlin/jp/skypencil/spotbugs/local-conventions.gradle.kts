import net.ltgt.gradle.errorprone.errorprone
import net.ltgt.gradle.errorprone.CheckSeverity

plugins {
    java
    jacoco
    id("com.diffplug.spotless")
    id("net.ltgt.errorprone")
}

repositories {
    mavenCentral()
}

dependencies {
    errorprone("com.google.errorprone:error_prone_core:2.9.0")
    errorprone("com.uber.nullaway:nullaway:0.9.2")
}

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(17))
	}
}

tasks.compileJava {
	options.compilerArgs.add("-Aproject=${project.group}/${project.name}")
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(17)
    options.errorprone {
      disableWarningsInGeneratedCode.set(true)
      check("NullAway", CheckSeverity.ERROR)
      option("NullAway:AnnotatedPackages", "jp.skypencil.spotbugs.cli")
    }
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

spotless {
    java {
        googleJavaFormat()
        licenseHeader("/* Copyright (C) \$YEAR Kengo TODA */")
    }
    kotlin {
        ktlint()
        licenseHeader("/* Copyright (C) \$YEAR Kengo TODA */")
    }
    kotlinGradle {
        ktlint()
    }
}
