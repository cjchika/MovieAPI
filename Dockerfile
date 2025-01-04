FROM openjdk:22-jdk
WORKDIR /app
COPY target/movieapi.jar app.jar
EXPOSE 8080
LABEL authors="cjchika"
ENTRYPOINT ["java", "-jar", "app.jar"]