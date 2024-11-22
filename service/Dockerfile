FROM openjdk:24-jdk-slim

COPY ./build/libs/service.jar app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]