

plugins {
    `application`
    `local-conventions`
    id("org.mikeneck.graalvm-native-image") version "1.4.1"
}

val picocliVersion: String = "4.6.2"

group = "jp.skypencil.spotbugs"

dependencies {
    kapt("info.picocli:picocli-codegen:$picocliVersion")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.github.spotbugs:spotbugs:4.4.2")
    implementation("info.picocli:picocli:$picocliVersion")
    implementation("org.slf4j:slf4j-simple:1.8.0-beta4")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
}

kapt {
    arguments {
        arg("project", "${project.group}/${project.name}")
    }
}

application {
    mainClass.set("jp.skypencil.spotbugs.cli.App")
}

nativeImage {
    graalVmHome = System.getenv("JAVA_HOME")
    mainClass = "jp.skypencil.spotbugs.cli.App"
    buildType { build ->
        build.executable(main = "jp.skypencil.spotbugs.cli.App")
    }
    executableName = "spotbugs"
    outputDirectory = file("$buildDir/executable")
    arguments(
        "--no-fallback"
    )
}

generateNativeImageConfig {
    enabled = true
    byRunningApplication {
        arguments("--help")
    }
    byRunningApplication {
        arguments("--aux", "${System.getenv("JAVA_HOME")}/lib/jrt-fs.jar", "$buildDir/libs/spotbugs-cli-$version.jar")
    }
}

defaultTasks("spotlessApply", "build")
