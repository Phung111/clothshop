# Giai đoạn Build
FROM maven:3-openjdk-8 AS build
WORKDIR /app

COPY . .
RUN mvn clean package -DskipTests

# Giai đoạn Run
FROM openjdk:8-jdk-slim
WORKDIR /app

COPY --from=build /app/target/your-app.jar your-app.jar
EXPOSE 8080 

ENTRYPOINT ["java", "-jar", "your-app.jar"]