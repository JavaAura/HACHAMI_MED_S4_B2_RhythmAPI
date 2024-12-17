# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the application JAR file into the container
COPY target/Rhythm-0.0.1-SNAPSHOT.jar app.jar

# List the contents of the /app directory for debugging
RUN ls -l /app

# Set the correct permissions (optional but recommended)
RUN chmod +x /app/app.jar

# Expose the port that your application runs on
EXPOSE 8088

# Run the application
ENTRYPOINT ["java", "-jar", "-Dserver.port=8088", "app.jar"]
