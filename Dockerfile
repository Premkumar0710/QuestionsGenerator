# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the local code to the container
COPY . .

# Install Maven (if necessary)
RUN apt-get update && apt-get install -y maven

# Build the application with Maven (use mvn clean install)
RUN mvn clean install

# Expose the port that the app will run on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "target/QuestionsGenerator.jar"]


