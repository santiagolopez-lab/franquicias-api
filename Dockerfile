# Simple Dockerfile for Franquicias API
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

# Install bash and curl
RUN apk add --no-cache bash curl

# Copy entire project
COPY . .

# Make gradlew executable and build only the final JAR
RUN chmod +x ./gradlew && \
    ./gradlew :app-service:bootJar -x test --no-daemon

# Runtime stage
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Install curl for health checks
RUN apk add --no-cache curl

# Copy the built JAR from builder
COPY --from=builder /app/applications/app-service/build/libs/*.jar app.jar

# Create logs directory
RUN mkdir -p logs

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=120s --retries=5 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
