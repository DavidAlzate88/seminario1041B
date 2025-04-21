FROM gradle:8.12.1-jdk17-alpine AS builder
COPY --chown=gradle:gradle . /app
WORKDIR /app
RUN chown -R gradle:gradle /home/gradle/.gradle
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