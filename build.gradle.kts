plugins {
    kotlin("jvm") version "2.3.21"
    kotlin("plugin.spring") version "2.3.21"
    id("org.springframework.boot") version "4.1.0"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "cz.developerthomas"
version = "0.0.1-SNAPSHOT"
description = "issue-tracker-jooq"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:junit-jupiter:1.21.4")
    testImplementation("org.testcontainers:postgresql:1.21.4")

    // Web
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-webmvc")

    // Serialization
    implementation("tools.jackson.module:jackson-module-kotlin")

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // Database
    implementation("org.flywaydb:flyway-database-postgresql")
    implementation("org.springframework.boot:spring-boot-starter-flyway")
    runtimeOnly("org.postgresql:postgresql")

    // JOOQ
    implementation("org.springframework.boot:spring-boot-starter-jooq")

    // Security
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
}

sourceSets {
    main {
        kotlin.srcDir("build/generated-sources/jooq")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.register("jooqCodegen") {
    group = "jOOQ"
    description = "Starts a temporary PostgreSQL database, applies Flyway migrations, and generates jOOQ sources."
    dependsOn(":codegen:generateJooq")
}