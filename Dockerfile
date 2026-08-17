FROM gradle:9.2.1-jdk25-ubi AS builder

WORKDIR /app

COPY . .

RUN gradle shadowJar --no-daemon


FROM eclipse-temurin:25-jre-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/bot.jar bot.jar

ENTRYPOINT ["java", "-jar", "bot.jar"]