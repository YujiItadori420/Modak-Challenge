# Modak-Challenge

# Rate-Limited Notification Service
This project is a sample application that handles the sending of email notifications to recipients. It allows sending different types of notifications and enforces limitations on the number of notifications that can be sent based on the type and recipient.


## Technologies Used
- Java
- Spring Boot
- JPA (Java Persistence API)
- JUnit
- MySql
- GitHub

## Project Setup

1. **Clone the Repository**

   To get started with this project, clone the repository from GitHub:

   ```bash
   git clone <https://github.com/YujiItadori420/Modak-Challenge>

2. **Run in Docker**
```shell
FROM openjdk:17-alpine
ARG JAR_FILE=build/libs/app.jar
COPY ${JAR_FILE} /srv/app.jar

COPY src/main/resources/application.properties /srv

RUN addgroup -S appuser && adduser -S appuser -G appuser
RUN chown -R appuser. /srv/

WORKDIR /srv

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar","--spring.config.location=file:/srv/application.properties"]
```

3. **Create Database**

```
CREATE DATABASE `user`;
```
