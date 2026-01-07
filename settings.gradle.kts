pluginManagement {
    repositories {
        maven { url = uri("https://maven.architectury.dev/") }
        maven("https://maven.neoforged.net/releases")
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "PotionTotems"