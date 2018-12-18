import org.gradle.kotlin.dsl.*
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.plugin.SpringBootPlugin.*

val springCloudVersion by extra { "Greenwich.RC1" }
val kotlinVersion = "1.3.11"
val braveVersion = "5.6.0"

buildscript {
	repositories {
		mavenCentral()
		maven("https://repo.spring.io/snapshot")
		maven("https://repo.spring.io/milestone")
	}
}

plugins {
	val kotlinVersion = "1.3.11"
	kotlin("jvm") version kotlinVersion
	kotlin("plugin.spring") version kotlinVersion
	id("org.springframework.boot") version "2.1.1.RELEASE"
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
	implementation("io.zipkin.brave:brave-bom:$braveVersion")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("com.squareup.retrofit2:retrofit:2.5.0")
	implementation("ch.qos.logback:logback-core")
	implementation("ch.qos.logback:logback-classic")
	implementation("io.zipkin.brave:brave-instrumentation-okhttp3:$braveVersion")
	implementation("io.zipkin.brave:brave-context-slf4j:$braveVersion")
	implementation("io.opentracing.brave:brave-opentracing") { exclude(module = "brave-tests") }
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.springframework.cloud:spring-cloud-starter-sleuth") {
		exclude(group = "io.zipkin.brave", module = "brave-context-log4j2")
	}
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test") { exclude(module = "junit") }
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("org.junit.jupiter:junit-jupiter-api")
	testImplementation("io.rest-assured:spring-web-test-client")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntime("org.junit.jupiter:junit-jupiter-engine")
}

tasks.withType<Test> { useJUnitPlatform() }

