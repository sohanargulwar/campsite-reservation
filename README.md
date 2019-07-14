# campsite-reservation

Steps to setup the environment

  a. make sure you have docker on local machine. If not download one from https://www.docker.com/
  
  b. Unzip postgres docker.zip and use command `docker-compose up -d`
  This will create a docker running on 9899 port with postgres database. 
  The database, user and respective tables will be created along with the docker.
  
  c. Once databse in setup, build project using `mvn clean install`. The target jar will be created in `/target` folder
  
  d. Use `java -jar <path_to_jar>`  The application will run on port 9899 configured in `application.yml` file
  
  e. To get information on model and API's use link `http://localhost:9899/swagger-ui.html#/`
