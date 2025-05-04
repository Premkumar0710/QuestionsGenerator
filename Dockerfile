# Use an official Maven image to build the app
FROM maven:3.8.4-openjdk-17 AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and source code into the container
COPY pom.xml .
COPY src ./src

# Build the app using Maven
RUN mvn clean install -DskipTests

# Now use a smaller image to run the application
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built jar file from the builder image
COPY --from=builder /app/target/QuestionsGenerator.jar .

# Command to run the application
CMD ["java", "-jar", "target/QuestionsGenerator.jar"]


