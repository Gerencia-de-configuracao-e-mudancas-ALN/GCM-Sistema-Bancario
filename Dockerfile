FROM openjdk:17-jdk-slim

WORKDIR /app

ADD sistema-bancario-backend/target/sistema-bancario-backend-1.0-SNAPSHOT.jar   /app/bankApi-docker.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "bankApi-docker.jar"]