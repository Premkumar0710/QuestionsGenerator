# Use a Maven image to build the app
FROM maven:3.8-openjdk-17 AS builder

# Set the working directory
WORKDIR /app

# Copy your source code to the Docker image
COPY . .

# Build the application with Maven (skip tests for faster build)
RUN mvn clean install -DskipTests

# Create a new stage for the final runtime image
FROM openjdk:17-jdk-slim

# Set the working directory in the final image
WORKDIR /app

# Copy the built JAR from the builder image using a wildcard (to avoid hardcoding)
COPY --from=builder /app/target/*SNAPSHOT.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
