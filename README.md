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

## Specification
**[Swagger with the app running locally](localhost:8080/docs)**

## Project Setup
1. ### Create local databases with MySql and Docker
```
docker pull --platform linux/x86_64 mysql:8.0
docker run --platform linux/x86_64 --name mysql -p 3306:3306 -d -e 'MYSQL_ROOT_PASSWORD=P4ssw0rd!' mysql:8.0
```
2. ### Log in to mysql
```
docker exec -it mysql bash
mysql -u root -p
  P4ssw0rd!
```

3. ### Create DBs
```
CREATE DATABASE `user`;
```