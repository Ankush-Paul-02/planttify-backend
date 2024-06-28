#FROM ubuntu:latest
#LABEL authors="ankus"
#
#ENTRYPOINT ["top", "-b"]

# Use OpenJDK 17 as the base image
FROM openjdk:17-jre-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot application JAR file into the container
COPY target/spring-hello-web-0.0.1-SNAPSHOT.jar /app/spring-hello-web.jar

# Expose the port that the Spring Boot application uses
EXPOSE 8080

# Command to run the Spring Boot application when the container starts
CMD ["java", "-jar", "spring-hello-web.jar"]
