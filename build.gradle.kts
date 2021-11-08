plugins {
    `application`
    `local-conventions`
}

val picocliVersion: String = "4.6.2"

group = "jp.skypencil.spotbugs"

dependencies {
    kapt("info.picocli:picocli-codegen:$picocliVersion")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.github.spotbugs:spotbugs:4.4.2")
    implementation("info.picocli:picocli:$picocliVersion")
    implementation("org.slf4j:slf4j-simple:2.0.0-alpha5")

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

defaultTasks("spotlessApply", "build")
