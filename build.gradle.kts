plugins {
    kotlin("jvm") version "2.0.20"
}

group = "ie.setu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.codehaus.jettison:jettison:1.4.1")

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(16)
}

