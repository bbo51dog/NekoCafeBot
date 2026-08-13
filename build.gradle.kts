plugins {
    application
    kotlin("jvm") version "2.3.20"
    id("com.gradleup.shadow") version "9.3.0"
}

group = "net.bbo51dog"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://m2.dv8tion.net/releases")
    maven ("https://jitpack.io")
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("net.dv8tion:JDA:6.5.0")
    implementation("com.github.kittinunf.fuel:fuel:2.3.1")
    implementation("com.github.kittinunf.fuel:fuel-json:2.3.1")
    implementation("dev.arbjerg:lavaplayer:2.2.7")
    implementation("club.minnced:jdave-api:0.1.8")
    implementation("club.minnced:jdave-native-linux-x86-64:0.1.8")
    implementation("club.minnced:jdave-native-linux-aarch64:0.1.8")
    implementation("club.minnced:jdave-native-win-x86-64:0.1.8")
    implementation("club.minnced:jdave-native-darwin:0.1.8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
}

application {
    mainClass.set("net.bbo51dog.nekocafebot.MainKt")
}

tasks.shadowJar {
    archiveFileName = "bot.jar"
    minimize()
}

kotlin {
    jvmToolchain(25)
}

tasks.test {
    useJUnitPlatform()
}