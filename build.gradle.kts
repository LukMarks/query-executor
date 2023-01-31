plugins {
    java
    application
    id("com.github.johnrengelman.shadow") version "7.1.2"

}

group = ""
version = "1.0"

repositories {
    mavenCentral()
}

val javaVersion = "8"

dependencies {
    //implementation("com.mysql:mysql-connector-j:8.0.31")
    implementation("com.google.guava:guava:31.1-jre")
    implementation("com.opencsv:opencsv:3.8")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.14.1")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("org.json:json:20220924")
    implementation("org.jetbrains:annotations:23.0.0")

}

tasks.jar { enabled = false }

// And enables shadowJar task
artifacts.archives(tasks.shadowJar)

tasks.shadowJar {
    archiveFileName.set("${rootProject.name}.jar")

    val dependencyPackage = "${rootProject.group}.dependencies.${rootProject.name.toLowerCase()}"
    relocate("arrow", "${dependencyPackage}.arrow")
    relocate("com.cryptomorin.xseries", "${dependencyPackage}.xseries")
}


tasks.withType<JavaCompile> {
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
    options.apply {
        encoding = "UTF-8"
    }
}

application {
    mainClass.set("org.queryExecuter.QueryExecutor")
}
