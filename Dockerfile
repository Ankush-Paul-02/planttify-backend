# Use Eclipse Temurin OpenJDK 17 as the base image
FROM eclipse-temurin:17-jre-focal

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot application JAR file into the container
COPY target/Hack4Bengal-0.0.1-SNAPSHOT.jar /app/Hack4Bengal.jar

# Expose the port that the Spring Boot application uses
EXPOSE 8080

# Command to run the Spring Boot application when the container starts
ENTRYPOINT ["java", "-jar", "/app/Hack4Bengal.jar"]
