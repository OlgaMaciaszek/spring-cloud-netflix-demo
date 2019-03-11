# Sample Credit Card application eco-system

- new card applications registered via `card-service`
- user registered via `user-service`
- `fraud-service` called by `card-service` and `user-service` to verify 
card applications and new users
- `ignored` service

#Existing setup with Spring Cloud Netflix

##Client side load-balancing using Ribbon

- Ribbon used via `@LoadBalanced` `RestTemplate`
- Ribbon configuration modified via `@RibbonClient`

##Apps communicating via Zuul:
- Routes automatically resolved by name
- Routes explicitly defined in properties
- Ignored services set by properties
- Integrated Hystrix

##Hystrix Circuit Breaker
- `@HystrixCommand`
- Hystrix Dashboards `http://localhost:8086/hystrix`
- Hystrix streams

##Turbine
- Hystrix streams collected automatically  `http://localhost:8086/turbine.stream`
- Apps to track configured via properties and resolved via Eureka Discovery Client