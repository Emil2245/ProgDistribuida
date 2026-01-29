plugins {
    id("java")
    id("io.quarkus") version "3.29.3"
    id("io.freefair.lombok") version "9.1.0"

}
val quarkusVersion = "3.29.3"

group = "com.programacion.distribuida"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}
dependencies {
    implementation(enforcedPlatform("io.quarkus.platform:quarkus-bom:${quarkusVersion}"))
    implementation("io.quarkus:quarkus-hibernate-orm")
    implementation("io.quarkus:quarkus-hibernate-orm-panache")
    implementation("io.quarkus:quarkus-jdbc-postgresql")
    implementation("io.quarkus:quarkus-arc")

// https://mvnrepository.com/artifact/org.modelmapper/modelmapper
    implementation("org.modelmapper:modelmapper:2.1.1")

    //RestC
    implementation("io.quarkus:quarkus-rest-client")
    implementation("io.quarkus:quarkus-rest-client-jsonb")

    //docker
    implementation("io.quarkus:quarkus-container-image-docker")

    //reciliencia
    implementation("io.quarkus:quarkus-smallrye-fault-tolerance")

    implementation("io.quarkus:quarkus-rest")
    implementation("io.quarkus:quarkus-rest-jsonb")

//SERVICE DISCOVERY - STORK
    implementation("io.quarkus:quarkus-smallrye-stork")
//    implementation("io.smallrye.stork:stork-service-discovery-static-list")
    implementation("io.smallrye.stork:stork-service-discovery-consul")
    implementation("io.smallrye.reactive:smallrye-mutiny-vertx-consul-client")

    // health (MicroProfile / SmallRye)
    implementation("io.quarkus:quarkus-smallrye-health")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}

repositories {
    mavenCentral()
    mavenLocal()
}

