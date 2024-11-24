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
    implementation("org.codehaus.jettison:jettison:1.5.4")
    implementation ("com.thoughtworks.xstream:xstream:1.4.21")

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(16)
}

