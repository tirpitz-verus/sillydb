plugins {
    id("java-library")
    id("maven-publish")
}

group = "mlesiewski"
version = "0.12.1"

java {
    withSourcesJar()
    withJavadocJar()
    modularity.inferModulePath.set(true)
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/tirpitz-verus/sillydb")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                name.set("SillyDB")
                description.set("a silly database in java")
                url.set("https://github.com/tirpitz-verus/sillydb")

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://raw.githubusercontent.com/tirpitz-verus/sillydb/main/LICENSE")
                    }
                }
            }
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.slf4j", "slf4j-api", "2.0.0-alpha1")
    implementation("io.reactivex.rxjava3", "rxjava", "3.0.7")

    testImplementation("org.junit.jupiter", "junit-jupiter", "5.7.0")
    testImplementation("org.assertj", "assertj-core", "3.18.0")
}

tasks.test {
    useJUnitPlatform {
        excludeTags.add("slow")
    }
}

tasks.register<Test>("testSlow") {
    useJUnitPlatform {
        includeTags.add("slow")
    }
}

tasks.compileJava {
    // use the project's version or define one directly
    options.javaModuleVersion.set(provider { project.version as String })
}

tasks.javadoc {
    (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    (options as StandardJavadocDocletOptions).addStringOption("Xwerror", "-quiet")
}

// compile in a different process to save GC in main Gradle demon
tasks.withType<JavaCompile>().configureEach {
    options.isFork = true
}
