# CaL

Build using [Spring Boot](https://spring.io/projects/spring-boot)

## Application
- http://localhost:8080/
- http://localhost:8080/explorer
- http://localhost:8080/swagger
- http://localhost:8080/actuator
- http://localhost:8080/actuator/health
- http://localhost:8080/h2-console
    - url: jdbc:h2:mem:cal
    - username: sa
    - password: admin
    - select count(*) from ITEMS
- SPRING_PROFILES_ACTIVE=postgres

```
curl -X GET http://localhost:8080/items | jq
curl -X POST http://localhost:8080/items -H 'Content-Type: application/json' -d '{"title" : "foo", "from" : "2023-04-07T21:58:06.456318","to" : "2023-04-08T06:58:06.456318","description" : "bar"}' | jq
```

```
for i in {1..100}
do
  item=$(echo '{"title" : "$i", "from" : "2023-04-07T21:58:06.456318","to" : "2023-04-08T06:58:06.456318","description" : "$i"}' \
    | jq '.title = "'"${i}"'"' \
    | jq '.description = "'"${i}"'"')
  curl -X POST http://localhost:8080/items -H 'Content-Type: application/json' -d ${item} | jq
done
```

```
for i in {1..1000}
do
    curl -X GET http://localhost:8080/items
done
```

