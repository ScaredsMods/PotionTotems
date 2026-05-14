import net.neoforged.moddevgradle.dsl.ModModel
import net.neoforged.moddevgradle.dsl.RunModel
import org.slf4j.event.Level

plugins {
    id("java")
    id("idea")
    id("maven-publish")
    id("net.neoforged.moddev") version "2.0.140"
    id("me.shedaniel.unified-publishing") version "0.1.13"
    id("com.diffplug.spotless") version("6.19.0")
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


// Dependencies
val resourcefulLibVersion : String by project
val fzzyConfigVersion : String by project
val emiVersion : String by project
val kffVersion : String by project

// Dev
var env = project.properties["env"]
val devVersion : String by project


group = modGroupId
version = modVersion

repositories {
    mavenCentral()
    maven {
        name = "Kotlin for Forge"
        setUrl("https://thedarkcolour.github.io/KotlinForForge/")
    }
    maven {
        // location of the maven that hosts JEI files since January 2023
        name = "Jared's maven"
        url = uri("https://maven.blamejared.com/")
    }
    maven {
        // location of a maven mirror for JEI files, as a fallback
        name = "ModMaven"
        url = uri("https://modmaven.dev")
    }
    maven { url = uri("https://maven.teamresourceful.com/repository/maven-public/") }
    maven {
        name = "FzzyMaven"
        url = uri("https://maven.fzzyhmstrs.me/")
    }
    maven {
        name = "TerraformersMC"
        url = uri("https://maven.terraformersmc.com/")
    }
}



java {
    toolchain {
        // Mojang ships Java 21 to end users starting in 1.20.5, so mods should target Java 21.
        languageVersion = JavaLanguageVersion.of(25)
    }
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

sourceSets {
    getByName("main") {
        resources.srcDir("src/generated/resources")
    }
}


neoForge {
    version = neoVersion


    runs {
        val client : RunModel by creating {
            client()
            programArguments.addAll("--username=ScaredRabbitNL", "--uuid=67e129a0-7954-4ad0-bc39-d2ecf97e7a1a")
            systemProperty("neoforge.enabledGameTestNamespaces", modId)
        }

        val client2 : RunModel by creating {
            client()
            systemProperty("neoforge.enabledGameTestNamespaces", modId)
            programArguments.addAll("--username=ScaredRabbitNL2")
        }

        val server : RunModel by creating {
            server()
            programArgument("--nogui")
            systemProperty("neoforge.enabledGameTestNamespaces", modId)
        }
        val gameTestServer : RunModel by creating {
            type = "gameTestServer"
            systemProperty("neoforge.enabledGameTestNamespaces", modId)
        }
        val data : RunModel by creating {
            clientData()
            programArguments.addAll("--mod", modId, "--all", "--output", file("src/generated/resources/").getAbsolutePath(), "--existing", file("src/main/resources/").getAbsolutePath())
        }

        configureEach {
            systemProperty("forge.logging.markers", "REGISTRIES")
            logLevel = Level.DEBUG
        }
    }

    mods {
        // define mod <-> source bindings
        // these are used to tell the game which sources are for which mod
        // mostly optional in a single mod project
        // but multi mod projects should define one per mod
        modId.let {
            val sourceSet : ModModel by creating {
                sourceSet(sourceSets.main.get())
            }
        }
    }

}


configurations {
    runtimeClasspath.get().extendsFrom(create("localRuntime"))
}

dependencies {
    jarJar("com.teamresourceful.resourcefullib:resourcefullib-neoforge-$mcVersion:$resourcefulLibVersion")
    implementation("com.teamresourceful.resourcefullib:resourcefullib-neoforge-$mcVersion:$resourcefulLibVersion")
    implementation("me.fzzyhmstrs:fzzy_config:${fzzyConfigVersion}+neoforge")

    //implementation("thedarkcolour:kotlinforforge-neoforge:$kffVersion")

    //compileOnly("dev.emi:emi-neoforge:${emiVersion}:api")
    //runtimeOnly("dev.emi:emi-neoforge:${emiVersion}")
}
tasks {

    withType<ProcessResources> {
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
    withType<JavaCompile> {
        options.encoding = "UTF-8" // Use the UTF-8 charset for Java compilation
    }
    withType<Jar> {
        when(env) {
            "dev" -> archiveFileName = "${modName}-${modVersion}.$env+$devVersion.jar"
            "release" -> archiveFileName = "$modName-${modVersion}.jar"
        }
    }
    register("uploadArtifacts") {
        group = "upload"
        when(env) {
            "release" -> {
                dependsOn("publishUnified")
                println("Release $modVersion: Artifacts were published to curseforge and modrinth!")
            }
            "dev" -> println("Dev environment found. No artifacts were published!")
        }
    }
}

spotless {
    java {
        targetExclude(
            "src/main/java/io/github/scaredsmods/potion_totems/item/TotemItem.java",
            "src/main/java/io/github/scaredsmods/potion_totems/mixin/client/ClientPacketListenerMixin.java",
            "src/main/java/io/github/scaredsmods/potion_totems/mixin/LivingEntityMixin.java"
        )
        licenseHeaderFile(file("HEADER"))
        removeUnusedImports()
        indentWithTabs()
        trimTrailingWhitespace()
        endWithNewline()
    }
}

if (env != "dev") {
    unifiedPublishing {
        project {
            displayName = "Potion Totems [$modVersion]"
            version = modVersion
            changelog = file("CHANGELOG.md").readText()
            releaseType = "release"
            gameVersions = listOf("1.21.1")
            gameLoaders = listOf("neoforge")


            mainPublication(tasks.jar.get())
            relations {
                depends { listOf("resourceful-lib", "fzzy_config") }
                includes {}
                optional {}
                conflicts {}
            }
            curseforge {
                token = providers.environmentVariable("CF_TOKEN").orElse("CF_TOKEN").get()
                var projectId = "1329049"
                id = projectId
                mainPublication(tasks.jar.get())
            }
            modrinth {
                token = providers.environmentVariable("MODRINTH_TOKEN").orElse("MODRINTH_TOKEN").get()
                id = "QNJVJEMv"
                mainPublication(tasks.jar.get())
            }
        }
    }
}

// IDEA no longer automatically downloads sources/javadoc jars for dependencies, so we need to explicitly enable the behavior.
idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}

fun setEnv(value : String) {
    env = value
}


