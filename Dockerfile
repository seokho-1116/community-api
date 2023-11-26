FROM adoptopenjdk/openjdk11

COPY ./build/libs/community-0.0.1-SNAPSHOT.jar community-api.jar

ENTRYPOINT ["java", "-jar", "community-api.jar"]