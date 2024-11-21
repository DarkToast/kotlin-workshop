import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    alias(libs.plugins.org.springframework.boot)
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    alias(libs.plugins.org.jetbrains.kotlin.plugin.jpa)
    alias(libs.plugins.org.jetbrains.kotlin.plugin.spring)
}

dependencies {
    implementation(libs.spring.boot.starter.actuator)
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.boot.starter.logging)

    implementation(libs.kotlin.logging.jvm) {
        exclude(group = "org.slf4j", module = "slf4j-api")
    }

    implementation(libs.h2)
    implementation(libs.kotlinx.coroutines.core.jvm)

    runtimeOnly(libs.jaxb.api)
    runtimeOnly(libs.kotlin.reflect)

    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.junit.jupiter)
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        showStandardStreams = true
        exceptionFormat = TestExceptionFormat.FULL
    }
}