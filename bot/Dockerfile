FROM openjdk:17-oracle

ARG JAR_FILE=bot/target/bot-1.0-SNAPSHOT.jar

WORKDIR /app

COPY ${JAR_FILE} /app/bot.jar

ENTRYPOINT ["java", "-jar", "bot.jar"]