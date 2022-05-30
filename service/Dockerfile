FROM openjdk:11-jdk-slim-stretch

COPY ./build/libs/service.jar app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]