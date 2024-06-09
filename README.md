# CyberGrid Interview Assignment

This repository contains the source of the application, described in the CyberGrid Interview Assignment 
(see [assignment.pdf](assignment.pdf)). The application exposes a REST API for manipulation of Product objects.
The project has two main components:
* [docker](docker) directory, containing the code needed to build postgres and wildfly images and run them in containers,
* [services](services) maven module, containing the [product service implementation](services/product-service/impl), 
  which contains the source code of the application. The module is packaged as a war build-time and can be deployed
  to an application server such as Wildfly.

## Setting up the application locally

To set up the local development environment, go to the [docker](docker) directory and run `docker compose up --build`.
What happens when you run this command is the following:
1. A [postgres](docker/postgres) image is created based on the official postgres docker image. A postgres container is
   created and launched.
2. A [wildfly](docker/wildfly) image is created. RedHat UBI is taken as a base and then dependencies such as Java are 
   installed. Wildfly is downloaded and set up. A wildfly container is created and launched. The container launches
   the Wildfly server. Our application gets deployed automatically, as it was copied into Wildfly deployments directory.

After docker initialization is completed, the local environment and the application are ready to be used.

## Using the application

The application exposes a simple REST API for manipulation of Product objects, locally available at the URL
http://localhost:8080/services/product. Some example data is already filled into the database deploy-time using Flyway 
migrations. The API defines the following endpoints:
* [GET /{id}](http://localhost:8080/services/product/33458aac-211a-4151-885a-fe8c9947cfe1) - returns the product matching
  the given id. Returns status 404 if product for given id is not found.
* [GET /](http://localhost:8080/services/product?min_price=10.00&max_price=1000&name=example&description=example%20product) -
  returns all products matching given parameters `name`, `min_price`, `max_price` and `description`. Matching is 
  case-insensitive and non-exact.
* POST / - creates a new product based on the request body, auto-generating a UUID for its id.
* PUT /{id} - updates an existing product based on given id. Returns status 404 if product for given id is not found.
* DELETE /{id} - deletes an existing product based on given id.

A collection of HTTP requests is available in [cybergrid-interview.postman_collection.json](cybergrid-interview.postman_collection.json). 
To use it directly, import it into [Postman](https://www.postman.com/). 

Database is accessible at http://localhost:5432 with username `postgres` and password `postgres`, or by connecting
to its docker container using command `docker exec -it postgres /bin/bash` and running `psql -U postgres` once inside 
the container.

## Next steps

Some potential next steps of this project are:
* Writing **integration tests**, which call the ProductController inside a real deployment with an underlying database and
  a non-mock repository, thus testing the whole stack of actors.
* Setup **Swagger** for automatic API definition generation.
* Separate flyway migrations into profiles, to allow using different profiles for different deployment/use-cases. For example,
  example data is beneficial for local development, but it should not be generated on production environments. On the
  other hand, we need database definition migrations everywhere.
