FROM openjdk:17-jdk-slim
COPY target/service-discovery-1.0-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]