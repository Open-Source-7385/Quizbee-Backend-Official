# Etapa 1: Build
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copiar archivos de configuración
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
COPY mvnw.cmd .

# Copiar código fuente
COPY src ./src

# Construir la aplicación
RUN chmod +x mvnw && ./mvnw clean package -DskipTests

# Etapa 2: Runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiar el JAR desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto (Render usa $PORT)
EXPOSE 8090

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-Dserver.port=${PORT}", "-jar", "app.jar"]
