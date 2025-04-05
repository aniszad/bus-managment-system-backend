# Use the official OpenJDK image
FROM openjdk:17-jdk-slim as builder

# Set the working directory
WORKDIR /BusSystem

# Copy the Gradle wrapper and build files
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

# Copy the source code
COPY src src

# Build the application
RUN ./gradlew build --no-daemon

# Create a smaller image for the runtime
FROM openjdk:17-jre-slim

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/build/libs/your-app-name.jar .

# Expose the port the app will run on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "your-app-name.jar"]
