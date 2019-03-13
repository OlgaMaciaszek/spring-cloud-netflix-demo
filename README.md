# Sample Credit Card application eco-system

After running all the apps execute POST at `localhost:8080/application` passing 
`cardApplication.json` as body.

```bash
http POST localhost:8080/application < cardApplication.json
```

- new card applications registered via `card-service`
- user registered via `user-service`
- `fraud-service` called by `card-service` and `user-service` to verify 
card applications and new users


```bash
http GET olgahost:8083/ignored/test
```
```bash
http GET olgahost:8083/ignored/test/allowed
```
- `ignored` service with `test` endpoint returning 404 via Proxy and `/test/allowed` 
endpoint returning response from the service.

```
+-------+                         +-------------+       +-------------+          +-------+             +---------------+ +-----------------+ +-------+
| User  |                         | CardService |       | UserService |          | Proxy |             | FraudVerifier | | IgnoredService  | | PRoxy |
+-------+                         +-------------+       +-------------+          +-------+             +---------------+ +-----------------+ +-------+
    |                                    |                     |                     |                         |                  |              |
    | Register application               |                     |                     |                         |                  |              |
    |----------------------------------->|                     |                     |                         |                  |              |
    |                                    |                     |                     |                         |                  |              |
    |                                    | Create new user     |                     |                         |                  |              |
    |                                    |------------------------------------------>|                         |                  |              |
    |                                    |                     |                     |                         |                  |              |
    |                                    |                     |     Create new user |                         |                  |              |
    |                                    |                     |<--------------------|                         |                  |              |
    |                                    |                     |                     |                         |                  |              |
    |                                    |                     | Verify new user     |                         |                  |              |
    |                                    |                     |-------------------->|                         |                  |              |
    |                                    |                     |                     |                         |                  |              |
    |                                    |                     |                     | Verify new user         |                  |              |
    |                                    |                     |                     |------------------------>|                  |              |
    |                                    |                     |                     |                         |                  |              |
    |                                    |                     |                     |           User verified |                  |              |
    |                                    |                     |                     |<------------------------|                  |              |
    |                                    |                     |                     |                         |                  |              |
    |                                    |                     |       User verified |                         |                  |              |
    |                                    |                     |<--------------------|                         |                  |              |
    |                                    |                     |                     |                         |                  |              |
    |                                    |        User created |                     |                         |                  |              |
    |                                    |<--------------------|                     |                         |                  |              |
    |                                    |                     |                     |                         |                  |              |
    |                                    |                     |                     |                         |                  | User created |
    |                                    |<------------------------------------------------------------------------------------------------------|
    |                                    |                     |                     |                         |                  |              |
    |                                    | Verify card application                   |                         |                  |              |
    |                                    |-------------------------------------------------------------------->|                  |              |
    |                                    |                     |                     |                         |                  |              |
    |                                    |                     |                     Card application verified |                  |              |
    |                                    |<--------------------------------------------------------------------|                  |              |
    |                                    |                     |                     |                         |                  |              |
    |        Card application registered |                     |                     |                         |                  |              |
    |<-----------------------------------|                     |                     |                         |                  |              |
    |                                    |                     |                     |                         |                  |              |
```

```
+-------+                         +-------+         +-----------------+
| User  |                         | Proxy |         | IgnoredService  |
+-------+                         +-------+         +-----------------+
    |                                 |                      |
    | IgnoredService/Test             |                      |
    |-------------------------------->|                      |
    |                                 |                      |
    |                             404 |                      |
    |<--------------------------------|                      |
    |                                 |                      |
    | IgnoredService/Test/Allowed     |                      |
    |-------------------------------->|                      |
    |                                 |                      |
    |                                 | Get allowed          |
    |                                 |--------------------->|
    |                                 |                      |
    |                         Allowed |                      |
    |<--------------------------------|                      |
    |                                 |                      |
```
#Setup using new stack

##Client side load-balancing using LoadBalancerClient

- Ribbon used via `@LoadBalanced` `WebClient`
- Ribbon configuration modified via `@LoadBalancerClient`

##Apps communicating via Gateway:
- Routes have to be explicitly defined
- Possibility to configure routes either via properties or Java configuration
- All headers passed by default
- Routes matched using predicates, requests modified using filters

##Spring Cloud Circuit Breaker + Resilience4J
- Interactions defined using injected `CircuitBreakerFactory` via the `create()` method
- HTTP call and fallback method defined
- Circuit breaker configuration modified in `Customizer<CircuitBreaker` bean 
in a `@Configuration` class 
- Resilience4J used underneath

##Micrometer + Prometheus
- HTTP traffic monitoring using Micrometer + Prometheus