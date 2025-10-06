FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests
RUN cp target/*.jar app.jar
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/app.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]