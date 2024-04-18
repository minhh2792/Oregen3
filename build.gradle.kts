plugins {
    `java-library`
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.codemc.io/repository/maven-public/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://repo.bg-software.com/repository/api/")
    maven("https://repo.oraxen.com/releases")
    maven("https://jitpack.io")
}

dependencies {
    implementation(files("libs/CommentedConfiguration.jar"))
    implementation("com.github.cryptomorin:XSeries:9.10.0") { isTransitive = false }

    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
    compileOnly("net.md-5:bungeecord-chat:1.20-R0.2") {
        isTransitive = false
    }
    compileOnly("com.github.MilkBowl:VaultAPI:1.7.1")
    compileOnly("me.clip:placeholderapi:2.11.5")
    compileOnly("com.bgsoftware:SuperiorSkyblockAPI:1.11.1")
    compileOnly("io.th0rgal:oraxen:1.173.0")
    compileOnly("com.github.LoneDev6:api-itemsadder:3.6.1")
    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")
}

group = "me.banbeucmas"
version = "1.8.0"
java.sourceCompatibility = JavaVersion.VERSION_17

tasks {
    processResources {
        filesMatching(listOf("plugin.yml")) {
            expand("version" to project.version)
        }
    }
    compileJava {
        options.encoding = Charsets.UTF_8.name()
    }
    assemble {
        dependsOn(shadowJar)
    }
    shadowJar {
        minimize()
        relocate("com.cryptomorin.xseries", "me.banbeucmas.oregen3.xseries")
        archiveFileName.set("Oregen3-${project.version}.jar")
        exclude("META-INF/**")
    }
}
