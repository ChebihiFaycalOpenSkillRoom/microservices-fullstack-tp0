plugins {
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    id("io.spring.dependency-management") version "1.1.4"
}

group = "openskillroom.ma"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.spring.web)
    implementation(libs.spring.validation)
    implementation(libs.spring.actuator)
    implementation(libs.jakson.kotlin)
    testImplementation(libs.spring.test)

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
