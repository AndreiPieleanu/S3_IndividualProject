# Use a Java base image
FROM gradle:7.6-jdk17

# Set the working directory
WORKDIR /opt/app

# Copy the built JAR file to the working directory
COPY andreipieleanu/andreipieleanu/build/libs/andreipieleanu-0.0.1-SNAPSHOT.jar ./

# Set the entry point to run the JAR file
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar andreipieleanu-0.0.1-SNAPSHOT.jar"]