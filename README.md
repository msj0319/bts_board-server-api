# Bithumb Tech Academy Final Project: Bithumb NFT Service 구현
##Board(Community) Server API
### docker-compose.yml
```yml
version: '1'
services:                       # service 목록을 정의
  mongo:                        # service의 이름: mongoDB
    image: "mongo:4.4.3"        # 해당 service에서 사용할 image
    container_name: board       # 해당 컨테이너 이름       
    restart: always             # container를 실행할 때 항상 이미 수행 중이라면 재시작 수행
    ports:                      # service port를 정의
      - 27017:27017             # local:container
    environment:                # 환경변수를 정의
      - MONGO_DATA_DIR=/data/db
      - MONGO_LOG_DIR=/dev/null
    volumes:                    # container -> local로 mount 할 수 있음
      - ./data/db:/data/db      # local 경로
  app:
    build: .
    restart: update
    ports:
      - 8083:8083
    depends_on:
      - mongo
```
### Dockerfile
```dockerfile
FROM openjdk:8-jdk-alpine
LABEL maintainer="gaeddongie13@gmail.com"
VOLUME /tmp
ARG JAR_FILE=./build/libs/*.jar
ADD ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://localhost/bts-board-db","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
```