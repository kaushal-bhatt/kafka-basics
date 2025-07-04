plugins {
	java
	id("org.springframework.boot") version "3.4.5"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.kaushal"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp
	implementation("com.squareup.okhttp3:okhttp:4.9.3")
	implementation("org.apache.kafka:kafka-clients:3.7.1")
	implementation("org.slf4j:slf4j-api:1.7.36")
	implementation("org.slf4j:slf4j-simple:1.7.36")
	// https://mvnrepository.com/artifact/com.launchdarkly/okhttp-eventsource
	implementation("com.launchdarkly:okhttp-eventsource:2.5.0")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
