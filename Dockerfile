FROM gradle:8.2.1-jdk17-alpine AS builder
COPY --chown=gradle:gradle . /app
WORKDIR /app
RUN gradle build --no-daemon

# Etapa 2: runtime
FROM amazoncorretto:17-alpine-jdk
WORKDIR /app
COPY --from=builder /app/build/libs/seminario1041B-0.0.1-SNAPSHOT.jar app.jar
RUN chmod 444 app.jar

# Seguridad: usuario no root
RUN addgroup -g 1000 appuser && \
    adduser -u 1000 -G appuser -s /bin/sh -D appuser && \
    chown -R appuser:appuser /app /tmp

USER appuser
ENTRYPOINT ["java", "-jar", "/app.jar"]