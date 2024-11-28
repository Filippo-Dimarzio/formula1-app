plugins {
    kotlin("jvm") version "2.0.20"
    id("org.jetbrains.dokka") version "1.9.20"
    jacoco

}

group = "ie.setu"
version = "formula1-app-1.0.jar"

repositories {
    mavenCentral()
}

dependencies {
    testimplementation()
// dependencies for logging
    implementation("io.github.oshai:kotlin-logging-jvm:7.0.0")
    implementation("org.slf4j:slf4j-simple:2.0.16")
//For Streaming to XML and JSON
    implementation("com.thoughtworks.xstream:xstream:1.4.21")
    implementation("org.codehaus.jettison:jettison:1.5.4")
// For generating a Dokka Site from KDoc
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.9.20")

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(16)
}




fun testimplementation() {

}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "MainKt" // Ensure your main class name is correct
    }
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    // Include all project outputs (compiled classes, resources)
    from(sourceSets.main.get().output)

    // Include runtime dependencies into the fat jar
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get()
            .filter { it.name.endsWith(".jar") }
            .map { zipTree(it) }
    })
}
