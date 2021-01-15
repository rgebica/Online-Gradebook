## Online Gradebook

 Project for Engineering Thesis at State Higher Vocational School in Tarnów 2021

## Data

    PostgreSQL - Database managment
    DataGrip - DataGrip provides context-sensitive code completion, helping you to write SQL code faster. Completion is aware of the tables structure, foreign keys,        and even database objects created in code you're editing.
    Toad Data Modeler - Toad Data Modeler is a database design tool allowing users to visually create, maintain, 
    and document new or existing database systems, and to deploy changes to data structures across different platforms.

## Client - Frontend/UI

    JavaScript - is a lightweight, interpreted programming language
    React - React is an open-source, front end, JavaScript library for building user interfaces or UI components.
    Bootstrap - Bootstrap is a free and open-source CSS framework directed at responsive, mobile-first front-end web development.
    Bootstrap Table - An extended table to the integration with some of the most widely used CSS frameworks.

## Server - Backend

    JDK - Java™ Platform, Standard Edition Development Kit
    Spring Boot - Framework to ease the bootstrapping and development of new Spring Applications
    Maven - Dependency Management
    JSON Web Token - Encode or Decode JWTs

## Libraries and Plugins

    Lombok - Never write another getter or equals method again, with one annotation your class has a fully featured builder, 
    Automate your logging variables, and much more.
    Swagger - Open-Source software framework backed by a large ecosystem of tools that helps developers design, build, document, and consume RESTful Web services.
    Hibernate - Hibernate ORM is an object-relational mapping tool for the Java programming language. 
    It provides a framework for mapping an object-oriented domain model to a relational database.
    JPA - The Java Persistence API (JPA) is one possible approach to ORM. Via JPA the developer can map, store, update and retrieve data from relational databases to       Java objects and vice versa. JPA can be used in Java-EE and Java-SE applications. JPA is a specification and several implementations are available.
    Spring Security - Spring Security is a Java/Java EE framework that provides authentication, authorization and other security features for enterprise applications.
    
## Others

    git - Free and Open-Source distributed version control system
    Docker - A set of platform as a service products that use OS-level virtualization to deliver software in packages called containers.

## External Tools & Services

    Mailtrap - Safe Email Testing for Staging & Development.
    Postman - API Development Environment (Testing Docmentation)
    gitignore.io - Create useful .gitignore files for your project.
    
## Testing
    
    Spock and Groovy - Spock is a testing and specification framework for Java and Groovy applications. What makes it stand out from the crowd is its beautiful and highly expressive specification language. Thanks to its JUnit runner, Spock is compatible with most IDEs, build tools, and continuous integration servers. Spock is inspired from JUnit, jMock, RSpec, Groovy, Scala, Vulcans, and other fascinating life forms.

## Description

Application corresponding to an electonic gradebook. App allows of creating an account, which on the basis of an e-mail will determine our function and will send a confirmation link. What is more,this application displays grades along with an average grade for each subject, midterm grade, attendance and absence; also with divisions into subjects, as well as attendance-absence balance. System also calculate weight average, presence count, presence percentage. Teacher can add semester and final grades 

## ERD

![PostgreSQL 9_5](https://user-images.githubusercontent.com/50657893/104745217-1f5a2880-574e-11eb-981b-6b36d2e8a106.jpeg)

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

    resources/templates - contains mailt Template.

    test/ - contains unit and integration tests

    pom.xml - contains all the project dependencies
    
## Installing

Running the application with Maven

    $ git clone https://github.com/rgebica/Online-Gradebook.git
    $ cd Online-Gradeebook
    $ mvn spring-boot:run

## Deployment
Deploying to Heroku

    Download and install the Heroku CLI - https://devcenter.heroku.com/articles/heroku-cli
    log in to your Heroku account heroku login
    set git remote heroku to the heroku app url, example heroku git:remote -a Online-Gradebook
    Add and Commit any pending changes to git
    push the code to heroku to deploy the app, example git push heroku master
    
## Documentation

    Swagger - http://localhost:8080/swagger-ui.html- Documentation & Testing
    SwaggerHub - https://app.swaggerhub.com/apis/rgebica/online-gradebook/13.0#/ Documentation & Testing

## Features to do

    Dark Mode
    Generate results PDF file
    Exams for students
    Full responsiveness

In the end, I hope you enjoyed the application and find it useful. Application is still under development

If you like this project, please:

    Give feedback,
    
    Give it a 🌟.

    Happy Coding ...* 🙂

    
