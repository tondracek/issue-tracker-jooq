plugins {
    kotlin("jvm")
}

group = "cz.developerthomas"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // JOOQ
    implementation(libs.jooq)
    implementation(libs.jooq.meta)
    implementation(libs.jooq.codegen)

    // Flyway
    implementation("org.flywaydb:flyway-core:11.14.1")
    implementation("org.flywaydb:flyway-database-postgresql:11.14.1")

    // Database
    implementation("org.testcontainers:postgresql:1.21.4")
    implementation("org.postgresql:postgresql:42.7.8")
    runtimeOnly("org.slf4j:slf4j-simple:1.7.36")
}

kotlin {
    jvmToolchain(21)
}

tasks.register<JavaExec>("generateJooq") {
    group = "jOOQ"
    description = "Generates jOOQ sources"

    inputs.dir("../src/main/resources/db/migration")
    outputs.dir("../build/generated-sources/jooq")

    classpath = sourceSets.main.get().runtimeClasspath
    mainClass.set("CodegenKt")
}