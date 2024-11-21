plugins {
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
}

dependencies {
    implementation(libs.logstash.logback.encoder)
    implementation(libs.kotlinx.coroutines.core.jvm)
    runtimeOnly(libs.kotlin.logging.jvm)

    testImplementation(libs.kotest.assertions.core.jvm)
    testImplementation(libs.kotest.runner.junit5.jvm)
    testImplementation(libs.junit.jupiter)
}

tasks.test {
    useJUnitPlatform()
}