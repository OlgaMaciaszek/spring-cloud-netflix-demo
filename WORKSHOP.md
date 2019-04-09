# Spring Cloud Workshops

- Distributed configuration with Spring Cloud Config
- Service registration and discovery with Spring Cloud Eureka
- Client side load balancing, including RestTemplate integration with Spring Cloud Commons
- Easy rest clients with Spring Cloud OpenFeign
- Circuit breaker/Fault tolerance library with Spring Cloud CircuitBreaker and Spring Cloud Hystrix
- Proxying with Spring Cloud Gateway
- Messaging with Spring Cloud Stream
- Distributed tracing with Spring Cloud Sleuth

## Setup

Start RabbitMQ and Zipkin

```bash
$ docker-compose -f docker/docker-compose.yml up -d
```

Clone the project with properties in git

```bash
$ mkdir -p ~/repo/
$ git clone https://github.com/marcingrzejszczak/spring-cloud-netflix-demo-git.git ~/repo/spring-cloud-netflix-demo-git
```
