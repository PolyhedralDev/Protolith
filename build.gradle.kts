plugins {
    `java-library`
}

group = "com.dfsek"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")

    api("io.vavr:vavr:0.10.4")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}