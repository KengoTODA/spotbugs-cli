plugins {
    `application`
    `local-conventions`
    id("org.mikeneck.graalvm-native-image") version "1.4.1"
}

val picocliVersion: String = "4.6.2"
val spotbugsVersion = "4.5.1-SNAPSHOT"

group = "jp.skypencil.spotbugs"

dependencies {
    kapt("info.picocli:picocli-codegen:$picocliVersion")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation(
        fileTree("$buildDir/spotbugs/spotbugs-$spotbugsVersion/lib").matching {
            include("*.jar")
        }
    )
    implementation("info.picocli:picocli:$picocliVersion")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
}

val unzipSpotBugs = tasks.register<Copy>("unzipSpotBugs") {
    dependsOn(gradle.includedBuild("spotbugs").task(":spotbugs:assemble"))

    from(zipTree(file("../spotbugs/spotbugs/build/distributions/spotbugs-$spotbugsVersion.zip")))
    into("$buildDir/spotbugs")
}

tasks.compileKotlin {
    dependsOn(unzipSpotBugs)
}

val copyXmlFiles = tasks.register<Copy>("copyXmlFiles") {
    dependsOn(gradle.includedBuild("spotbugs").task(":spotbugs:processResources"))
    from("../spotbugs/spotbugs/build/resources/main") {
        include("findbugs.xml", "messages.xml", "messages_fr.xml", "messages_ja.xml", "default.xsl")
    }
    into(sourceSets["main"].output.resourcesDir!!)
}

tasks.classes {
    dependsOn(copyXmlFiles)
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
        "-H:+AllowIncompleteClasspath",
        "-H:+AllowJRTFileSystem",
        "-H:IncludeResources=findbugs\\.xml$",
        "-H:IncludeResources=messages.*\\.xml$",
        "-H:DefaultLocale=en",
        "-H:IncludeLocales=ja",
        "-H:IncludeResourceBundles=de.tobject.findbugs.messages,edu.umd.cs.findbugs.FindBugsAnnotationDescriptions",
        "-H:+ReportExceptionStackTraces",
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
