# usersServiceProject

This project is used to manage users data and devided into two sub modules (microservices):

## UserService
This module responsible for actually manage the users data - fetching and modifying it. This module have a service (userService) that represents the contract that this module offers.

## restService
This module is a general service that exposing api for http requests and uses the needed modules to perform the tasks.

## Instructions for running the apps:
* make sure to have a local mongoDB running in your computer - "mongod" in terminal
* open it as a two different projects in the IntelliJ (there is two sub directories - restService and userService)
* mvn clean install for each one of them (userService is first)
* run each of them

This is the API you can use:
* http://localhost:8080/getuser?userId=<some user id> - GET request
* http://localhost:8080/getusernames?sorted=<true/false> - GET request
* http://localhost:8080/adduser - POST request with json body that looks like this: {	"name": "Lior", "lastName": "Pog", "birthYear": 1992 }
