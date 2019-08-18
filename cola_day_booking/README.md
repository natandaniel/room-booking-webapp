# Cola Day Booking 

A web application for _The Cola Day_ : a day when Pepsi and Coca Cola gather in a building and hold business meetings from 10am to 9pm.
Authentified users of the application can book rooms amongst 20 rooms and time slots from 10am to 9pm.

Built with :
  1. _Java 8_
  1. _Spring Boot_ for autoconfiguration
  1. _Spring Web_ to implement the **MVC pattern** 
  1. _Spring Data JPA_ for object/relational mapping with the in memory H2 relational database
  1. _Spring Data Rest_ for CRUD repositories exposed as RESTful APIs
  1. _H2 in memory database + MySQL_ for data storage and management
  1. _Spring Security_ for authentication and policies
  1. _Web Sockets_ to implement the **publish-subscribe pattern** enabling live updates
  1. _Bootstrap_ for a quick design of the app
  1. _Thymeleaf_ the chosen Java secondary view template
  1. _React + JavaScript (ES6)_ the chosen JavaScript main view template

Run app with Maven in root folder : mvn spring-boot:run 

Available users to authentify in application : 
  - username : u001 pasword : u001
  - username : u002 password : u002

Package into a jar : mvn clean install 

Run as a jar : java -jar /target/consensys-booking-1.0.0-SNAPSHOT-spring-boot.jar
