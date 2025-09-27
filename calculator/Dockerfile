FROM openjdk:17-jdk-slim as builder

WORKDIR /app

COPY . .

RUN chmod +x gradlew
RUN ./gradlew :calculator:build -x test


FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=builder /app/calculator/build/libs/calculator-*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]