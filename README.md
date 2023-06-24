# Test Task for Java Developer Position

In application.properties you can find two variants to start the program:

1) Simple start Application and use MongoDB
2) Start by using Docker

To start all by Docker you need to do:

  mvn clean install

  docker build -t docker_image .

  docker pull mongo

  docker run -d --name mongo-on-docker -p 27017:27017 mongo

  docker run -d --name springapplication-on-docker -p 8080:8080 docker_image

After the implementation of the project, the code was checked through SonarLint and all comments and tips were corrected for the cleanliness of the code and the avoidance of repetitions.

It was also important to test our project. Tests were created to test different scenarios of how our controllers work.

Also, SLF4J was added - a simple, implementation-independent, generalized interface for logging systems.


