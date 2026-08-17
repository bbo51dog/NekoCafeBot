FROM gradle:9.2.1-jdk25-ubi AS builder

WORKDIR /app

COPY . .

RUN gradle build --no-daemon


FROM eclipse-temurin:25

WORKDIR /app

COPY --from=builder /app/build/libs/bot.jar bot.jar

ENTRYPOINT ["java", "-jar", "bot.jar"]