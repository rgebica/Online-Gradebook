### Online Gradebook

## Project for Engineering Thesis at State Higher Vocational School in Tarnów 2021

## Built With

    Java - Programmin language (Backend)
    Java Script - Programming language (Frontend)
    Spring - Java Framework 
    React - Java Script Framework
    Spring Security
    Spring Data
    Spring Hibernate / JPA
    Spock / Groovy / JUnit - Testing
    Maven - Dependency Management
    PostgreSQL - Database 
    SwaggerUI / SwaggerHub / Postman - Testing and documentation
    

## Description

Application corresponding to an electonic gradebook. App allows of creating an account, which on the basis of an e-mail will determine our function and will send a confirmation link. What is more,this application displays grades along with an average grade for each subject, midterm grade, attendance and absence; also with divisions into subjects, as well as attendance-absence balance. System also calculate weight average, presence count, presence percentage. Teacher can add semester and final grades Application is still under development

## ERD

![PostgreSQL 9_5](https://user-images.githubusercontent.com/50657893/104745217-1f5a2880-574e-11eb-981b-6b36d2e8a106.jpeg)

## Use case



## Packages

    config - Security config and Swagger config;

    controllers - to listen to the client;

    dto - object that is used to encapsulate data, and send it from one subsystem of an application to another;

    exception - to hold custom exception handling;

    model - to hold our entities;

    repository - to communicate with the database;

    security - security configuration and json web token for authentication;

    service - to hold business logic;

    resources/ - Contains all the static resources, templates and property files.

    resources/templates - contains server-side templates which are rendered by Spring.

    test/ - contains unit and integration tests

    pom.xml - contains all the project dependencies

## Deployment
Deploying to Heroku

    Download and install the Heroku CLI
    log in to your Heroku account heroku login
    set git remote heroku to the heroku app url, example heroku git:remote -a spring-boot-app-template
    Add and Commit any pending changes to git
    push the code to heroku to deploy the app, example git push heroku master

## Documentation

    Swagger - http://localhost:8080/swagger-ui.html- Documentation & Testing
    SwaggerHub - https://app.swaggerhub.com/apis/rgebica/online-gradebook/13.0#/ Documentation & Testing
