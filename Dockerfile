FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY out/calculator-cli.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
