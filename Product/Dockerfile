# Use JDK 17 as base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the compiled jar from the target directory
COPY target/Product-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port (default for Spring Boot is 8080, you can change if necessary)
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
