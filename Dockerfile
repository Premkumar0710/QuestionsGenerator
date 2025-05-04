# Use a Maven image to build the app
FROM maven:3.8-openjdk-17 as builder

# Set the working directory
WORKDIR /app

# Copy your source code to the Docker image
COPY . .

# Build the application with Maven
RUN mvn clean install -DskipTests

# Check the files in the target directory
RUN ls /app/target

# Create a new stage for the final image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/target/QuestionsGenerator-0.0.1-SNAPSHOT.jar .

# Command to run the application
ENTRYPOINT ["java", "-jar", "QuestionsGenerator-0.0.1-SNAPSHOT.jar"]
