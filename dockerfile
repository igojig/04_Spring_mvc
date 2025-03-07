
FROM maven:3.9.4-eclipse-temurin-17 AS MAVEN_BUILD

COPY pom.xml /build/
COPY src /build/src/

WORKDIR /build/
RUN mvn package

FROM openjdk:17-alpine

WORKDIR /app

COPY --from=MAVEN_BUILD /build/target/spring-mvc-homework-0.0.1-SNAPSHOT.jar /app/

ENTRYPOINT ["java", "-jar", "spring-mvc-homework-0.0.1-SNAPSHOT.jar"]