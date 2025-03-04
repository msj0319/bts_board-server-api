FROM openjdk:8-jdk-alpine
LABEL maintainer="gaeddongie13@gmail.com"
VOLUME /tmp
ARG JAR_FILE=./build/libs/*.jar
ADD ${JAR_FILE} app.jar
EXPOSE 8083
ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://localhost/bts-board-db","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]