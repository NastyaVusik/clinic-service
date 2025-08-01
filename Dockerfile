FROM gradle:8.5-jdk17-alpine AS build
WORKDIR /app
COPY build.gradle* settings.gradle* ./
COPY gradle ./gradle
RUN gradle dependencies --no-daemon || true
COPY . .
RUN gradle clean bootJar --no-daemon

FROM eclipse-temurin:17-jre-alpine
ENV TZ=UTC
WORKDIR /app
COPY --from=build /app/build/libs/*.jar clinic-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "clinic-service.jar"]