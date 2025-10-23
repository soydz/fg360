# --- Etapa de Construcción (Builder) ---
FROM gradle:8.7-jdk21-focal AS builder
WORKDIR /app
COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradle ./gradle
RUN ./gradlew dependencies
COPY src ./src
RUN ./gradlew build --no-daemon -x test

# --- Etapa Final (Runtime) ---
FROM openjdk:21-slim
WORKDIR /app

# Se ha eliminado la línea "EXPOSE 8080"

COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]