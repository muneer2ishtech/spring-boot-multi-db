## Docker

### Docker build

- `DB_TO_USE` is required — must be one of: `h2`, `postgres`, `mysql`, `mariadb`
- arg for custom `SERVER_PORT` is optional, you can change to desired value or skip, if skipped it will use default `8080`

```
docker build . \
  --build-arg DB_TO_USE=h2 \
  -t "muneer2ishtech/ishtech-springboot-multidb-app:$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout)-h2"

```

- With custom `SERVER_PORT`

```
docker build . \
  --build-arg DB_TO_USE=postgres \
  --build-arg SERVER_PORT=8181 \
  -t "muneer2ishtech/ishtech-springboot-multidb-app:$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout)-postgres"

```

### Run with docker image

- Note: check and use version from pom.xml
  - Replace `x.y.z` with appropriate version number, e.g. `2.1.0` or `2.1.0-SNAPSHOT`
  - Replace `<db>` with the database variant: `h2`, `postgres`, `mysql`, `mariadb`
- Add option ` -d` if you want to run in background

- To run with H2 (in-memory, no external DB needed)

```
docker run \
  -p 8080:8080 \
  muneer2ishtech/ishtech-springboot-multidb-app:x.y.z-h2
```

- To run with PostgreSQL

```
docker run \
  -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:55432/multidb \
  muneer2ishtech/ishtech-springboot-multidb-app:x.y.z-postgres
```

- To connect to DB running as docker on the host machine, use `host.docker.internal` as the hostname
  - If `host.docker.internal` doesn't resolve by default then add `--add-host=host.docker.internal:host-gateway`

- To run with custom application port inside container
  - E.g.: Spring Boot runs on `8181`, exposed on `8282`

```
docker run \
  ...
  -e SERVER_PORT=8181 \
  -p 8282:8181 \
  muneer2ishtech/ishtech-springboot-multidb-app:x.y.z-h2
```


### Run with docker compose

- Each database variant has its own docker compose file
- Docker compose is self contained, so you don't need anything else other than docker

- To stop if running
    - `docker compose -f docker-compose-<db>.yml stop`

- To stop and remove including volumes and built images
    - `docker compose -f docker-compose-<db>.yml down -v --rmi=local`

- To build and start
    - You can prefix with env vars as in below example
    - Below args are optional, you can change to desired value or skip, if skipped they will use default value
        - `SERVER_PORT` — port Spring Boot runs on inside the container, if skipped defaults to `8080`
        - `SERVER_PORT_<DB>` — port the app is exposed on the host machine for that DB variant, if skipped defaults to `SERVER_PORT`
        - `DB_PORT_<DB>` — port the DB is exposed on the host machine, if skipped defaults to the DB's default port
        - `APP_VERSION` — version tag for the Docker image

- H2 (no external DB)

```
APP_VERSION=$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout 2>/dev/null) \
docker compose -f docker-compose-h2.yml up --build

```

- PostgreSQL

```
APP_VERSION=$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout 2>/dev/null) \
DB_PORT_POSTGRES=55432 \
docker compose -f docker-compose-postgres.yml up --build

```

- MySQL

```
APP_VERSION=$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout 2>/dev/null) \
DB_PORT_MYSQL=13306 \
docker compose -f docker-compose-mysql.yml up --build

```

- MariaDB

```
APP_VERSION=$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout 2>/dev/null) \
DB_PORT_MARIADB=23306 \
docker compose -f docker-compose-mariadb.yml up --build

```
