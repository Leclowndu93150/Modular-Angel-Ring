plugins {
    id("dev.prism")
}

group = "com.leclowndu93150"
version = "2.1.0"

prism {
    curseMaven()
    maven("BlameJared", "https://maven.blamejared.com/")

    metadata {
        modId = "modular_angelring"
        name = "Modular Angel Ring"
        description = "Adds a modular Angel Ring that you can tweak to your likings."
        license = "All Rights Reserved"
        author("Leclowndu93150")
    }

    publishing {
        changelogFile = "changelog.md"

        curseforge {
            accessToken = providers.environmentVariable("CURSEFORGE_TOKEN")
            projectId = "1037481"
        }

        dependencies {
            requires("curios")
        }
    }

    version("1.21.1") {
        parchmentMinecraftVersion = "1.21.1"
        parchmentMappingsVersion = "2024.11.17"

        neoforge {
            loaderVersion = "21.1.228"

            dependencies {
                implementation("curse.maven:curios-309927:6529130")
                runtimeOnly("mezz.jei:jei-1.21.1-neoforge:19.21.0.247")
            }
        }
    }

    version("26.1.2") {
        neoforge {
            loaderVersion = "26.1.2.124"

            dependencies {
                implementation("curse.maven:curios-309927:8463519")
                runtimeOnly("mezz.jei:jei-26.1.2-neoforge:29.47.0.110")
            }
        }
    }
}
