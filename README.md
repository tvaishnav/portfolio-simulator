1) This application comes with maven wrapper. Hence it can be built and executed on machine without maven installed.

2) Application be executed by the command:

   > mvnw spring-boot:run

3) By default, application runs simulation based on application.yml present inside the jar file. 

   For more control, you can specify your own application.properties (or application.yml) outside the jar file 
   and refer to it in command line property **spring.config.location**

   For instance,
   
   > mvnw clean package
   > java -jar -Dspring.config.location=<path to custom application[.properties|.yml]> <jar file name>
   
   Similarly, you can override the logging configuration by setting **logging.config** property on command line.