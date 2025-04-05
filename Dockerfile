# ---- Build Stage ----
FROM openjdk:17-jdk-slim as builder

WORKDIR /app

# Copy Gradle wrapper and build scripts
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

# Copy project source
COPY src src

# Build the application
RUN ./gradlew build --no-daemon

# ---- Runtime Stage ----
FROM openjdk:17-jre

WORKDIR /app

# Copy the built jar from builder
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
