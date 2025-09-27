FROM openjdk:17-jdk-slim as builder

WORKDIR /app

COPY . .

RUN chmod +x gradlew
RUN ./gradlew :rest:build -x test


FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=builder /app/rest/build/libs/rest-*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]