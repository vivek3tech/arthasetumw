# Stage 1: Build the application (optional if you build with GitHub Actions)
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:17-jdk-alpine

# Set a working directory
WORKDIR /app
COPY --from=build /app/target/arthasetu-0.0.1-SNAPSHOT.jar app.jar

# Expose the port Cloud Run will listen on
EXPOSE 8080

# Start the app
ENTRYPOINT ["java", "-jar", "app.jar"]
