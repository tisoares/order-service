# Order Service

### Installation

Docker compose command:

```shell
docker compose -f postgre-compose.yaml  up -d
```

Install project:

```shell
mvn clean install -U
```

Liquibase check changes:

```shell
mvn liquibase:status -Dliquibase.secureParsing=false
```

Liquibase apply changes:

```shell
mvn liquibase:update -Dliquibase.secureParsing=false
```

Run application:

```shell
mvn spring-boot:run -Dspring-boot.run.profiles=postgre
```

Swagger link [here](http://localhost:8080/swagger-ui/index.html)

Credentials:

```
username: admin@admin.com
password: 123456
```