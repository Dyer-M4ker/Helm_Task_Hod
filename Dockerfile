FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY build/libs/calculator-cli.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
