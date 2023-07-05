# Utiliza una imagen base de Java
FROM openjdk:17

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR al contenedor
COPY /build/libs/U-Tracker_API-0.0.1-SNAPSHOT.jar /app/U-Tracker_API-0.0.1-SNAPSHOT.jar

# Expone el puerto que utilizará la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "U-Tracker_API-0.0.1-SNAPSHOT.jar"]