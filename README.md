# Sample Credit Card application eco-system

After running all the apps execute POST at `localhost:9080/application` passing 
`cardApplication.json` as body.

```bash
http POST localhost:9080/application < cardApplication.json
```

- new card applications registered via `card-service`
- user registered via `user-service`
- `fraud-service` called by `card-service` and `user-service` to verify 
card applications and new users


```bash
http GET localhost:9083/ignored/test
```

```bash
http GET localhost:9083/ignored/test/allowed
```

- `ignored` service with `test` endpoint returning 404 via Proxy and `/test/allowed` 
endpoint returning response from the service.

```
+-------+                         +-------------+          +-------------+          +-------+             +---------------+ +-----------------+ +---------+
| User  |                         | CardService |          | UserService |          | Proxy |             | FraudVerifier | | IgnoredService  | | Turbine |
+-------+                         +-------------+          +-------------+          +-------+             +---------------+ +-----------------+ +---------+
    |                                    |                        |                     |                         |                  |               |
    | Register application               |                        |                     |                         |                  |               |
    |----------------------------------->|                        |                     |                         |                  |               |
    |                                    |                        |                     |                         |                  |               |
    |                                    | Create new user        |                     |                         |                  |               |
    |                                    |----------------------->|                     |                         |                  |               |
    |                                    |                        |                     |                         |                  |               |
    |                                    |                        | Verify new user     |                         |                  |               |
    |                                    |                        |-------------------->|                         |                  |               |
    |                                    |                        |                     |                         |                  |               |
    |                                    |                        |                     | Verify new user         |                  |               |
    |                                    |                        |                     |------------------------>|                  |               |
    |                                    |                        |                     |                         |                  |               |
    |                                    |                        |                     |           User verified |                  |               |
    |                                    |                        |                     |<------------------------|                  |               |
    |                                    |                        |                     |                         |                  |               |
    |                                    |                        |       User verified |                         |                  |               |
    |                                    |                        |<--------------------|                         |                  |               |
    |                                    |                        |                     |                         |                  |               |
    |                                    |           User created |                     |                         |                  |               |
    |                                    |<-----------------------|                     |                         |                  |               |
    |                                    |                        |                     |                         |                  |               |
    |                                    | Verify card application|                     |                         |                  |               |
    |                                    |----------------------------------------------------------------------->|                  |               |
    |                                    |                        |                     |                         |                  |               |
    |                                    |                        |                     Card application verified |                  |               |
    |                                    |<-----------------------------------------------------------------------|                  |               |
    |                                    |                        |                     |                         |                  |               |
    |        Card application registered |                        |                     |                         |                  |               |
    |<-----------------------------------|                        |                     |                         |                  |               |
    |                                    |                        |                     |                         |                  |               |
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
- Hystrix Dashboards `http://localhost:9086/hystrix`
- Hystrix streams

##Turbine
- Hystrix streams collected automatically  `http://localhost:9086/turbine.stream`
- Apps to track configured via properties and resolved via Eureka Discovery Client