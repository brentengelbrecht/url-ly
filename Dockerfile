FROM openjdk:8-jdk-alpine

COPY target/urlly-1.0.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]
