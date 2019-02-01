import org.gradle.kotlin.dsl.*
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.plugin.SpringBootPlugin.*

val springCloudVersion by extra { "Greenwich.RELEASE" }
val kotlinVersion = "1.3.20"

buildscript {
	repositories {
		mavenCentral()
		maven("https://repo.spring.io/snapshot")
		maven("https://repo.spring.io/milestone")
	}
}

plugins {
	val kotlinVersion = "1.3.20"
	kotlin("jvm") version kotlinVersion
	kotlin("plugin.spring") version kotlinVersion
	id("org.springframework.boot") version "2.1.2.RELEASE"
}

apply {
	plugin("io.spring.dependency-management")
}

group = "com.beltram"
version = "0.0.1-SNAPSHOT"

tasks.withType<KotlinCompile> {
	kotlinOptions {
		jvmTarget = "1.8"
		freeCompilerArgs = listOf("-Xjsr305=strict")
	}
}

repositories {
	mavenCentral()
	maven("https://repo.spring.io/snapshot")
	maven("https://repo.spring.io/milestone")
}

the<DependencyManagementExtension>().apply {
	imports {
		mavenBom(BOM_COORDINATES) { bomProperty("kotlin.version", kotlinVersion) }
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
	implementation("io.opentracing.brave:brave-opentracing") { exclude(module = "brave-tests") }
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.springframework.cloud:spring-cloud-starter-sleuth")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test") { exclude(module = "junit") }
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("io.rest-assured:spring-web-test-client")
	testRuntime("de.flapdoodle.embed:de.flapdoodle.embed.mongo")
	testImplementation("org.junit.jupiter:junit-jupiter-api")
	testRuntime("org.junit.jupiter:junit-jupiter-engine")
}

tasks.withType<Test> { useJUnitPlatform() }