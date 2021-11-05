plugins {
    `application`
    `local-conventions`
}

val jupiterVersion: String = "5.8.1"
val picocliVersion: String = "4.6.1"

group = "jp.skypencil.spotbugs"

dependencies {
    kapt("info.picocli:picocli-codegen:$picocliVersion")

    api("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.jetbrains.kotlin:kotlin-script-runtime")
    implementation("com.github.spotbugs:spotbugs:4.4.2")
    implementation("info.picocli:picocli:$picocliVersion")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$jupiterVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jupiterVersion")
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