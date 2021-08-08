# NBA_api
The ultimate goal of the standalone project is to develop our business services in the domain of event processing from basketball games, and then to adapt the Spring Boot Cloud service for our use cases, with the support of Docker and its tools.

Operating modes
The project should be usable in two modes:
Local development
Docker containers + Spring Cloud
This division is applied in professional development and regardless of specific business requirements.
Local development
Backup services (databases, message broker) are used through the Docker Compose tool.

Business services are booted as Spring Boot applications from the IDE, in Spring test and default profiles.
The operation of the service is checked through the test unit and directly through the REST API and by checking logs and accompanying support services.
Docker containers + Spring Cloud
We add and customize the Spring Cloud service.
All Spring Boot services (business, cloud) are Docker-insulated.
All Spring Boot services are configured to work in a cloud environment, in the development of Spring profiles.
Services are added to Docker Compose, through which it runs, outside the IDE.
The service configuration is externalized in a special Git repository, it is local enough.
Services log in to Spring Cloud upon launch, and then perform their business functionality.
In Docker Compose, the configuration is additional and ELK, in which the services write, upon startup.
Jenkins was installed through Docker Compose, a construction service was configured through it.

Overview of services and expected functionalities
Business services implemented:
pbp -service - "flow" of the match
game -service - event tracking
stats -service - processing events from the broker
Definition of ancillary services - PostgreSKL, RabbitMK:
docker-compose.iml
test variant in a separate folder
Business services are run from the IDE, use an ancillary service run via docker-compose and perform the required functionalities.i
Cloud services implemented and customized:
detection-service
config service
Written Dockerfile for each service, custom port.
Add services to docker-compose.iml.
Cloud configuration for individual services
Git repository msa-config with all configurations
config-service customized to use msa-config
Business services configured to log on to the Cloud
ELK added to docker-compose.iml
Business services are configured to send logs to ELK
Jenkins added it to docker-compose.iml, and then configured it to build individual services
