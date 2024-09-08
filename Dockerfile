# Use a base image with Java 22
FROM openjdk:22

# Copy the JAR package into the image
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} bankbook.jar

# Expose the application port
EXPOSE 8080

# Run the App
ENTRYPOINT ["java", "-jar", "/bankbook.jar"]