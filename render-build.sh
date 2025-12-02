#!/bin/bash
# Build script para Render

echo "🚀 Iniciando build de QuizBee Backend..."

# Dar permisos de ejecución a Maven Wrapper
chmod +x mvnw

# Compilar la aplicación sin ejecutar tests
./mvnw clean package -DskipTests

echo "✅ Build completado exitosamente!"
