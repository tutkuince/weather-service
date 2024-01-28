FROM openjdk:17-jdk-slim AS build

COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package

FROM openjdk:17-jdk-slim
WORKDIR weather-service
COPY --from=build target/*.jar weather-service.jar
ENTRYPOINT ["java", "-jar", "weather-service.jar"]