FROM amazoncorretto:17-alpine-jdk
VOLUME /tmp
WORKDIR /app
COPY build/libs/seminario1041B-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]