plugins {
    java
}

group = "io.github.tear360"
version = "1.0.0"
description = "Menu /settings avec les écrans natifs de Minecraft (dialogs) — Paper 26.1 à 26.3"

java {
    toolchain {
        // Minecraft 26.x s'exécute sur Java 25 (paper-api 26.1.2 est compilé en Java 25)
        languageVersion = JavaLanguageVersion.of(25)
    }
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    // Compilé contre la plus petite version supportée : tout ce que le plugin
    // utilise existe déjà dans 26.1.2, donc il tourne aussi sur 26.2 et 26.3.
    compileOnly("io.papermc.paper:paper-api:26.1.2.build.74-stable")
}

tasks.processResources {
    val props = mapOf("version" to project.version.toString())
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}
