#Existing setup with Spring Cloud Netflix

##Apps communicating via Zuul:
- Routes automatically resolved by name
- Ignored services set by properties
- Routes explicitly defined in properties
- Integrated Hystrix

##Hystrix Circuit Breaker
- `@HystrixCommand`
- Hystrix Dashboards `http://localhost:8086/hystrix`
- Hystrix streams

##Turbine
- Hystrix streams collected automatically  `http://localhost:8086/turbine.stream`
- Apps to track configured via properties and resolved via Eureka Discovery Client