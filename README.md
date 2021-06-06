# Production Plan

Application "Production-plan" is intended for control manufacturing process within production company.

##Main functions:
   - create new order;
   - edit selected order;  
   - search order by parameters.

##Requirements
### Database
Create database and schemas using repository/src/resources/dbchangelog.sql

##Compiling, running and debugging
###For Windows OS
Compile, test and package project
`mvn clean install`
Copy generated *.war from webapp/target to $CATALINA_HOME\webapps and run 
`catalina.bat  start`
To debug solution, run 
`catalina.bat jpda start`
Configure your IDE with remote debug configuration on default 8000 port.  

##Usage
Login to system (taken from repository/src/resources/dbchangelog.sql)
email: superAdmin@test.mail
password: admin123