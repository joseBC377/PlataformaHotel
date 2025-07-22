# Imagen base con Java 17
FROM eclipse-temurin:17-jdk-alpine

# Directorio de trabajo en el contenedor
WORKDIR /app

# Variable con la ruta del .jar
ARG JAR_FILE=target/*.jar

# Copia el jar compilado al contenedor
COPY ${JAR_FILE} app.jar

# Ejecuta la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]