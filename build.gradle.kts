plugins {
    id("java")
    id("java-library")
    id("idea")
    id("maven-publish")
    id("net.neoforged.moddev") version("2.0.115")
}

tasks.named<Wrapper>("wrapper").configure {
    distributionType = Wrapper.DistributionType.BIN
}
// Mod Properties
val modId : String by project
val modName : String by project
val modDescription : String by project
val modGroupId : String by project
val modVersion : String by project
val modLicense : String by project
val modAuthors : String by project

// Minecraft / Neo Properties
val loaderVersionRange : String by project
val mcVersion : String by project
val mcVersionRange : String by project
val neoVersion : String by project
val neoVersionRange : String by project
val parchmentMCVersion : String by project
val parchmentMappingsVersion : String by project

// Dependencies


group = modGroupId
version = modVersion

repositories {
    mavenCentral()
    maven {
        name = "Kotlin for Forge"
        setUrl("https://thedarkcolour.github.io/KotlinForForge/")
    }
}
neoForge {
    version = neoVersion
    parchment {
        mappingsVersion = parchmentMappingsVersion
        minecraftVersion = parchmentMCVersion
    }
    runs {
        create("client") {
            client()
            systemProperty("neoforge.enabledGameTestNamespaces", modId)
        }
        create("server") {
            server()
            programArgument("--nogui")
            systemProperty("neoforge.enabledGameTestNamespaces", modId)
        }
        create("gameTestServer") {
            type = "gameTestServer"
            systemProperty("neoforge.enabledGameTestNamespaces", modId)
        }
        create("data") {
            data()
            programArguments.addAll("--mod", modId, "--all", "--output", file("src/generated/resources/").getAbsolutePath(), "--existing", file("src/main/resources/").getAbsolutePath())
        }
        configureEach {
            systemProperty("forge.logging.markers", "REGISTRIES")
            logLevel = org.slf4j.event.Level.DEBUG
        }
    }
    mods {
        modId.let {
            create("sourceSet") {
                sourceSet(sourceSets.main.orNull)
            }
        }

    }
}

java.toolchain.languageVersion = JavaLanguageVersion.of(21)
sourceSets {
    getByName("main") {
        resources.srcDir("src/generated/resources")
    }
}


configurations {
    runtimeClasspath.get().extendsFrom(create("localRuntime"))
}

dependencies {

}

tasks.withType<ProcessResources>{
    val replaceProperties = mapOf(
        "minecraft_version" to mcVersion,
        "minecraft_version_range" to mcVersionRange,
        "neo_version" to neoVersion,
        "neo_version_range" to neoVersionRange,
        "loader_version_range" to loaderVersionRange,
        "mod_id" to modId,
        "mod_name" to modName,
        "mod_license" to modLicense,
        "mod_version" to modVersion,
        "mod_authors" to modAuthors,
        "mod_description" to modDescription
    )

    inputs.properties(replaceProperties)

    filesMatching("META-INF/neoforge.mods.toml") {
        expand(replaceProperties)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8" // Use the UTF-8 charset for Java compilation
}

// IDEA no longer automatically downloads sources/javadoc jars for dependencies, so we need to explicitly enable the behavior.
idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}




