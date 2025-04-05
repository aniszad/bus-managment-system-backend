# ---- Build Stage ----
FROM openjdk:17-jdk-slim as builder

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src src

RUN chmod +x gradlew
# Skip tests with -x test flag to avoid test failures during Docker build
RUN ./gradlew build --no-daemon -x test

# ---- Runtime Stage ----
FROM openjdk:17-slim

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]