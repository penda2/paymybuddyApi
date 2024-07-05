
# Paymybuddy

Money transfer application.


##  Physical data model

This model defines the structure of the database by representing its tables, columns and relations between tables.
## Installation
  This application is developed with: 

    Java 21
    Spring boot 3
    Maven 
    MySQL Workbench 
  To download Java 21
    https://www.oracle.com/fr/java/technologies/downloads/

  To download MySQL Workbench
    https://dev.mysql.com/downloads/workbench/

  To download Maven  
    https://maven.apache.org/download.cgi
    

## Run application locally
Don't forget to connect to the Mysql database before building the application.

  1 - Clone repository
    git clone https://github.com/penda2/paymybuddyApi.git

  2 - Go to the project directory, click on the address bar at the top of the window (where the folder path is displayed). Type "cmd" and press Enter. This will open Command Prompt directly to the location of this folder.

  3- To verify java version(21), run :

    java -version

  4 - Generate jar file in new taget folder by  runing :

    mvn clean package

  5 - Once the project is built, start the application using the following command:

    java -jar paymybuddy-0.0.1-SNAPSHOT.jar

  5 - Go to the browser and use registration or login page:

    http://localhost:8080/register 
    http://localhost:8080/login 
