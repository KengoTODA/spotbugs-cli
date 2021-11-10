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
    implementation(
        fileTree("$buildDir/spotbugs/spotbugs-4.4.3-SNAPSHOT/lib").matching {
            include("*.jar")
        }
    )
    implementation("info.picocli:picocli:$picocliVersion")
    implementation("org.slf4j:slf4j-simple:1.8.0-beta4") {
        isTransitive = false
    }

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
}

val unzipSpotBugs = tasks.register<Copy>("unzipSpotBugs") {
    dependsOn(gradle.includedBuild("spotbugs").task(":spotbugs:assemble"))

    from(zipTree(file("../spotbugs/spotbugs/build/distributions/spotbugs-4.4.3-SNAPSHOT.zip")))
    into("$buildDir/spotbugs")
}

tasks.compileKotlin {
    dependsOn(unzipSpotBugs)
}

kapt {
    arguments {
        arg("project", "${project.group}/${project.name}")
    }
    includeCompileClasspath = false
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
        "--no-fallback",
        "-H:+AllowIncompleteClasspath"
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
