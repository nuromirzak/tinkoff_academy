FROM openjdk:17-oracle

ARG JAR_FILE=scrapper/target/scrapper-1.0-SNAPSHOT.jar

WORKDIR /app

COPY ${JAR_FILE} /app/scrapper.jar

ENTRYPOINT ["java", "-jar", "scrapper.jar"]