FROM gradle:8.12.1-jdk17-alpine AS builder

WORKDIR /app

# Copy only Gradle files first to leverage caching
COPY --chown=gradle:gradle gradle /app/gradle
COPY --chown=gradle:gradle gradlew /app/gradlew
COPY --chown=gradle:gradle build.gradle /app/build.gradle
COPY --chown=gradle:gradle settings.gradle /app/settings.gradle

# Download dependencies
RUN gradle dependencies --no-daemon

# Copy the source code
COPY --chown=gradle:gradle src /app/src

# Build the application
RUN gradle build --no-daemon

FROM amazoncorretto:17-alpine-jdk
WORKDIR /app
ARG JAR_FILE=build/libs/seminario1041B-0.0.1-SNAPSHOT.jar
COPY --from=builder /app/${JAR_FILE} app.jar
RUN addgroup -g 1000 appuser && \
    adduser -u 1000 -G appuser -s /bin/sh -D appuser && \
    chown -R appuser:appuser /app /tmp

USER appuser
ENTRYPOINT ["java", "-jar", "/app.jar"]