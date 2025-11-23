plugins {
    id("java")
    id("war")
}

group = "com.prog"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    // https://mvnrepository.com/artifact/jakarta.ws.rs/jakarta.ws.rs-api
    implementation("jakarta.ws.rs:jakarta.ws.rs-api:4.0.0")
}

tasks.test {
    useJUnitPlatform()
}