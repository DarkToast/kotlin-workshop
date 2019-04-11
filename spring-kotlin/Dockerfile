FROM openjdk:11-jdk-slim-stretch

COPY ./target/spring-kotlin-*.jar app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]