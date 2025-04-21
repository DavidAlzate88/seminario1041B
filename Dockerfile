FROM amazoncorretto:17-alpine-jdk
VOLUME /tmp
WORKDIR /app
COPY build/libs/seminario1041B-0.0.1-SNAPSHOT.jar app.jar

# Crear un nuevo grupo y usuario llamado 'appuser' con un UID específico (opcional pero recomendado)
RUN addgroup -g 1000 appuser && \
    adduser -u 1000 -G appuser -s /bin/sh -D appuser && \
    chown -R appuser:appuser /app /tmp

# Cambiar al usuario 'appuser' para ejecutar la aplicación
USER appuser

ENTRYPOINT ["java","-jar","/app.jar"]