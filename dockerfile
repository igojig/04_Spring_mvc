
FROM maven:3.9.9-eclipse-temurin-17 AS MAVEN_BUILD

COPY pom.xml /build/
COPY src /build/src/

WORKDIR /build/
RUN mvn package -DskipTests

FROM bellsoft/liberica-openjdk-alpine:17.0.14-10

WORKDIR /app

COPY --from=MAVEN_BUILD /build/target/spring-mvc-homework-0.0.1-SNAPSHOT.jar /app/

ENTRYPOINT ["java", "-jar", "spring-mvc-homework-0.0.1-SNAPSHOT.jar"]