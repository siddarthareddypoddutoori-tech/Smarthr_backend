FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /workspace

COPY pom.xml .
COPY src ./src

RUN mvn -B -DskipTests package

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /workspace/target/smart-hr-backend-1.0.0.jar app.jar

ENV SPRING_PROFILES_ACTIVE=render \
    SPRING_DATASOURCE_URL=jdbc:postgresql://dpg-db4d4kt9fdbs73bj849g-a:5432/project_4d6z \
    SPRING_DATASOURCE_USERNAME=project_4d6z \
    SPRING_DATASOURCE_PASSWORD=9dmCswceZwBOvuv0eXqrlfDj5eM2gyl9 \
    SPRING_DATASOURCE_DRIVER_CLASS_NAME=org.postgresql.Driver \
    DB_URL=jdbc:postgresql://dpg-db4d4kt9fdbs73bj849g-a:5432/project_4d6z \
    DB_USERNAME=project_4d6z \
    DB_PASSWORD=9dmCswceZwBOvuv0eXqrlfDj5eM2gyl9 \
    JWT_SECRET=SmartHR-Secret-Key-Change-In-Production-2026-At-Least-32-Bytes \
    CORS_ALLOWED_ORIGINS=*

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]