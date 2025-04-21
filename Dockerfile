FROM gradle:8.12.1-jdk17-alpine AS builder

WORKDIR /app

# Copy only the wrapper script and make it executable
COPY gradlew /app/gradlew
RUN chmod +x /app/gradlew

# Copy the gradle directory (libraries) - let Docker handle ownership
COPY gradle /app/gradle

# Copy the rest of the Gradle build files
COPY build.gradle /app/build.gradle
COPY settings.gradle /app/settings.gradle

# Copy source code
COPY src /app/src

# Copy env.properties
COPY env.properties /app/env.properties

# Build
RUN gradle build -x test

FROM amazoncorretto:17-alpine-jdk
WORKDIR /app

ARG JAR_FILE=build/libs/seminario1041B-0.0.1-SNAPSHOT.jar
# Copia el archivo jar
COPY --from=builder /app/${JAR_FILE} app.jar

# Copia el archivo de configuración env.properties
COPY --from=builder /app/env.properties /app/env.properties

RUN addgroup -g 1000 appuser && \
    adduser -u 1000 -G appuser -s /bin/sh -D appuser && \
    chown -R appuser:appuser /app /tmp

USER appuser
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]