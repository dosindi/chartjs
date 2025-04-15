# ---- FROM eclipse-temurin:17-jdk-focal AS builder ----
 
# ---- Stage 1: Build the application ----
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# Set the working directory
WORKDIR /app

# Copy project files
COPY pom.xml .
COPY src ./src

# Build the project with production profile (minimizes frontend dependencies)
RUN mvn clean package -Pproduction -DskipTests

# ---- Stage 2: Create a minimal runtime image ----
FROM eclipse-temurin:17-jre AS runtime

# Set a non-root user for security
RUN addgroup --system appgroup && adduser --system appuser --ingroup appgroup
USER appuser

# Set working directory
WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
