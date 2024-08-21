# Giai đoạn Build
FROM maven:3-openjdk-8 AS build
WORKDIR /app

COPY . .
RUN mvn clean package -DskipTests

# Giai đoạn Run
FROM openjdk:8-jdk-slim
WORKDIR /app

COPY --from=build /app/target/clothshop-0.0.1-SNAPSHOT.jar clothshop.jar
EXPOSE 8080 

ENTRYPOINT ["java", "-jar", "clothshop.jar"]