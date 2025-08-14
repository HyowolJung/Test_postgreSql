plugins {
    id("java")
    id("application")
}

group = "org.example"
version = "1.0-SNAPSHOT"

application {
    // Main 클래스의 FQCN으로 설정 (패키지 없으면 "Main")
    mainClass.set("org.example.Main") // 예: com.example.Main
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.postgresql:postgresql:42.7.7")


    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // 테스트: JUnit 5
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")

    // 테스트: Testcontainers (PostgreSQL + JUnit5)
    testImplementation("org.testcontainers:postgresql:1.19.7")
    testImplementation("org.testcontainers:junit-jupiter:1.19.7")

    testRuntimeOnly("org.slf4j:slf4j-simple:2.0.12") // 경고 제거
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.test {
    useJUnitPlatform()
}

