plugins {
    id("java")

//    id("application")
    id("io.freefair.lombok") version "9.1.0"
    //argega el main dentro del manifest
    id("com.gradleup.shadow") version "9.2.0"
}



group = "com.prog"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    // https://mvnrepository.com/artifact/io.helidon.webserver/helidon-webserver
    implementation("io.helidon.webserver:helidon-webserver:4.3.2")
    // https://mvnrepository.com/artifact/io.helidon.webserver/helidon-webserver-json
    implementation("io.helidon.http.media:helidon-http-media-jsonp:4.3.2")
    implementation("io.helidon.http.media:helidon-http-media-jsonb:4.3.2")

    implementation("io.helidon.dbclient:helidon-dbclient-jdbc:4.3.2")
    implementation("io.helidon.dbclient:helidon-dbclient:4.3.2")
    implementation("io.helidon.dbclient:helidon-dbclient-hikari:4.3.2")
    runtimeOnly("io.helidon.config:helidon-config-yaml:4.3.2")
}

//application {
//    mainClass = "com.prog.Main"
//}


tasks.test {
    useJUnitPlatform()
}
tasks.jar{
    manifest{
        attributes["Main-Class"]="com.prog.ApplicationMain"
    }
}
tasks.shadowJar{
    mergeServiceFiles()
}