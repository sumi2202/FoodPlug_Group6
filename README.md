### SOFE4790U: Distributed Systems

## Final Project: FoodPlug - Distributed Food Ordering Application using Publish-Subscribe

Group 6

-----------------------------------------------

Our application makes use of a publish-subscribe approach through the use of two servers that handle different tasks; this application uses JeroMQ, a Java implementation of ZeroMQ.

### Adding project dependencies

In order to run the application, several dependencies must be configured. This application requires the use of the jeromq.jar file and a .jar file for the mysql-connector. These will be included in the repository.
These two files must be downloaded and then added as dependencies to ensure the application runs. If using the IntelliJ IDE, this can be done by going to Project Structure > Modules, where users will be able to
add modules through a "+" button. Choose the "JARS or Directories..." option, find the downloaded .jar files, ensure the checkmark is present in the box, and then press "Apply", and then "OK". 

In terms of connecting the databases, the Database.java file contains the configurations for the database - the password should be the user's mySQL workbench password. For JPA, there should be a plugin enabled and then a database connection was added with the schema used. The pom.xml file contains information about hibernate and the spring framework.

### Running the application

To run the application, the user can run the application using the IDE. 

