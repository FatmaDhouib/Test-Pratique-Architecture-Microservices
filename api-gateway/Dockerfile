# Utilise une image Java 25
FROM eclipse-temurin:25-jdk-alpine

# Répertoire de travail dans le conteneur
WORKDIR /app

# Copie le JAR compilé (Maven le place dans target/)
COPY target/*.jar app.jar

# Expose le port du service
EXPOSE 8090

# Lance l'application
ENTRYPOINT ["java", "-jar", "app.jar"]