plugins {
    id("java")
    id("com.google.cloud.tools.jib") version "3.4.2"
}

group = "org.example"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    val jettyVersion = "12.0.10"
    implementation("org.eclipse.jetty:jetty-server:${jettyVersion}")
    implementation("org.eclipse.jetty.ee10:jetty-ee10-servlet:${jettyVersion}")
    implementation("org.slf4j:slf4j-simple:2.0.13")
    compileOnly("jakarta.servlet:jakarta.servlet-api:6.0.0")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("com.squareup.okhttp3:okhttp:4.12.0")
    testImplementation("org.testcontainers:junit-jupiter:1.19.8")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
    systemProperties["org.slf4j.simpleLogger.defaultLogLevel"] = "debug"
    systemProperties["org.testcontainers.logging.enabled"] = "true"
}

jib {
    from {
        image = "openjdk:17-jdk-slim"
    }
    to {
        image = "hello-world-docker-image:latest"
    }
    container {
        mainClass = "org.example.HelloWorldServer"
        ports = listOf("8080")
        environment = mapOf("PORT" to "8080")
    }
}