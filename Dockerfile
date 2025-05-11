FROM maven:3.9.9-eclipse-temurin-24-alpine AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:23
COPY --from=build /target/trackflow-server-api.jar app.jar
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

LABEL authors="vimukthi-jayasanka"
