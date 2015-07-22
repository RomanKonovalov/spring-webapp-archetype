# spring-webapp-archetype

## About

It's a spring base web application archetype with the following basic functionality:

  *  Security (embedded Admin and User roles).
  *  Roles and accounts administration.
  *  Basic files storing functionality (utility).
  *  Utility access administration.
  
## Building

Clone, build and generate.

Example:

`mvn archetype:generate -DarchetypeGroupId=com.romif -DarchetypeArtifactId=spring-webapp-archetype
-DarchetypeVersion=1.0-SNAPSHOT -DgroupId=com.mycompany -DartifactId=fileserverapp
-Dpackage=com.mycompany.fileserverapp`
