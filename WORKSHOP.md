# Spring Cloud Workshops

- Distributed configuration with Spring Cloud Config
- Service registration and discovery with Spring Cloud Eureka 
	* mention that one can change this to Consul / Zookeeper
	* Eureka : (http://localhost:8761/)
- Client side load balancing, including RestTemplate integration with Spring Cloud Commons
- Easy rest clients with Spring Cloud OpenFeign
- Circuit breaker/Fault tolerance library with Spring Cloud CircuitBreaker and Spring Cloud Hystrix
- Proxying with Spring Cloud Gateway
	* Show the docs
- Messaging with Spring Cloud Stream
	* Rabbit console: http://localhost:15671
	* guest / guest
- Distributed tracing with Spring Cloud Sleuth
	* Zipkin : http://localhost:9411/
- Metrics with Micrometer and Prometheus

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

Clone the project for Prometheus

```bash
$ git clone https://github.com/marcingrzejszczak/prometheus.git
$ cd prometheus
$ git checkout demo_sc_netflix
$ docker-compose up -d
```