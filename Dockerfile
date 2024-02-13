FROM eclipse-temurin:21-jre

COPY *.jar /app/authentication-service.jar

WORKDIR /app

CMD ["java", "-jar", "authentication-service.jar"]