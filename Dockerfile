FROM adoptopenjdk/openjdk11:alpine
EXPOSE 8080

# Installs Java application.
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

