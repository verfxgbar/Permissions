plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "1.5.4"
    id("maven-publish")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://jitpack.io")
}

dependencies {
    paperweight.paperDevBundle("1.20.1-R0.1-SNAPSHOT")

}

tasks {
    assemble {
        dependsOn(reobfJar)
    }

    processResources {
        from(sourceSets.main.get().resources.srcDirs) {
            filesMatching("plugin.yml") {
                expand(
                    "name" to rootProject.name,
                    "version" to version
                )
            }
            duplicatesStrategy = org.gradle.api.file.DuplicatesStrategy.INCLUDE
        }
    }
    compileJava {
        options.encoding = "UTF-8"
    }
    getByName<Test>("test") {
        useJUnitPlatform()
    }
}
publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "org.gradle.sample"
            artifactId = "library"
            version = "1.1"

            from(components["java"])
        }
    }
}