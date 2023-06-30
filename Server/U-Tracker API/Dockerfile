FROM openjdk:17
ENV APP_NAME_FILE=U-Tracker_API-0.0.1-SNAPSHOT-plain.jar
COPY build/libs/$APP_NAME_FILE /app/$APP_NAME_FILE
ENTRYPOINT ["java", "-jar", "/app/$APP_NAME_FILE"]
