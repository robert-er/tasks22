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

## Documentation

API endpoints are documented using Swagger [./swagger-ui.html#/](http://rocky-sands-77117.herokuapp.com/swagger-ui.html#/)

Application INFO is avilable under Actuator endpoint [./actuator/info](http://rocky-sands-77117.herokuapp.com/actuator/info)

## Contributing
If You encounter any problems regarding operation, please let me know. This is a training application. Any comments/advice are welcome.