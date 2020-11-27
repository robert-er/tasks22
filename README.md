# KODILLA TASKS APPLICATION

Kodilla Tasks application is a training application implemented during the course 'Java Developer Plus, in Kodilla bootcamp. 

Tasks is java web application with using REST API. It can store tasks database. It can communicate with Trello boards via API endpoints.

## Demo
Backend is working on Heroku. Frontend is uploaded under: [https://robert-er.github.io/](https://robert-er.github.io/)

## Features
Possible to store tasks in Heroku Postgresql database. 
Functions:
- add a task to database
- read tasks from database
- edit task
- delete task
- mailing service: app send email periodically to my address with number of tasks stored in the database.

Possible to send a task as a card to one of my Trello boards. Possible to read Trello boards and lists on my Trello account to select place where card will be saved.

## Installation

The application works in the environment

```bash
Java 8
Gradle 6.4.1
```

#### Run at localhost

To start application on localhost, small changes are necessary. Need to setup your local database.

##### How to change database?

In file `src/main/resources/application.properties` there is a section `#HEROKU CONFIGURATION WITH POSTGRES`. 
You should put it under comments and add your localhost database configuration. 
For testing purposes I used MySQL version 8.0.21 local database, but you can use any database you have installed locally.
In my case, application.properties for local MySQL use looks like:

##### application.properties
```properties
# LOCAL DATABASE MYSQL CONFIGURATION
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database=mysql
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.datasource.url=jdbc:mysql://localhost:3306/task_crud?\
  serverTimezone=Europe/Warsaw&useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=robert
spring.datasource.password=robertpassword

#HEROKU CONFIGURATION WITH POSTGRES
#spring.jpa.hibernate.ddl-auto=update
#spring.jpa.database=postgresql
#spring.datasource.url=${JDBC_DATABASE_URL}
#spring.datasource.username=${JDBC_DATABASE_USERNAME}
#spring.datasource.password=${JDBC_DATABASE_PASSWORD}
```

Also need to change database dependencies in `build.gradle`: comment PostgreSQL dependency and uncomment MySQL dependency like below.

##### build.gradle
```java
dependencies {
    (...)
	//for localhost use
	implementation 'mysql:mysql-connector-java'

	//for Heroku use
        //	implementation 'org.postgresql:postgresql'
    (...)
``` 

## Documentation

API endpoints are documented using Swagger [./swagger-ui.html#/](http://rocky-sands-77117.herokuapp.com/swagger-ui.html#/)

Application INFO is avilable under Actuator endpoint [./actuator/info](http://rocky-sands-77117.herokuapp.com/actuator/info)

## Contributing
If You encounter any problems regarding operation, please let me know. This is a training application. Any comments/advice are welcome.