# spring-boot-multi-db
Spring Boot example with connecting to different DB

## Databases
- H2
- MariaDB
- MySQL
- PostgreSQL

### DB (with Docker if required)

- H2

```
http://localhost:8080/h2-console

SHOW TABLES;

```

- MariaDB

```
docker run --name multi-db-mariadb \
  -e MARIADB_ROOT_PASSWORD=rootpass \
  -e MARIADB_DATABASE=multidb \
  -e MARIADB_USER=multidbuser \
  -e MARIADB_PASSWORD=multidbpass \
  -p 53306:3306 \
  mariadb:10.6

```

```
mariadb -u multidbuser -pmultidbpass -D multidb

SHOW TABLES;
DESC TABLE gold_sample;
SHOW CREATE TABLE gold_sample\G

```

- MySQL

```
docker run --name multi-db-mysql \
  -e MYSQL_ROOT_PASSWORD=rootpass \
  -e MYSQL_DATABASE=multidb \
  -e MYSQL_USER=multidbuser \
  -e MYSQL_PASSWORD=multidbpass \
  -p 53306:3306 \
  mysql:8.4

```

```
mysql -u multidbuser -pmultidbpass -D multidb

SHOW TABLES;
DESC TABLE gold_sample;
SHOW CREATE TABLE gold_sample\G

```

- PostgreSQL

```
docker run --name multi-db-postgres \
 -e POSTGRES_DB=multidb \
 -e POSTGRES_USER=multidbuser \
 -e POSTGRES_PASSWORD=multidbpass \
 -p 55432:5432 \
 postgres:17

```

```
psql -U multidbuser -W -d multidb

\dt

\d gold_sample

```

## Build and Run

### Local
- Need
    - JDK21 or higher
    - Maven
    - Docker

- Build
    - If you want to build with tests then you need to pass spring profile, see next

```
mvn clean install -DskipTests -P h2
mvn clean install -DskipTests -P mariadb
mvn clean install -DskipTests -P mysql
mvn clean install -DskipTests -P postgres

```

- Test

```
mvn test -P h2       -Dspring.profiles.active=h2
mvn test -P mariadb  -Dspring.profiles.active=mariadb
mvn test -P mysql    -Dspring.profiles.active=mysql
mvn test -P postgres -Dspring.profiles.active=postgres

```

- Run
    - Ensure the port, db properties are correct in application-xxx.properties / application-xxx.yml

```
mvn spring-boot:run -P h2       -Dspring-boot.run.profiles=h2
mvn spring-boot:run -P mariadb  -Dspring-boot.run.profiles=mariadb
mvn spring-boot:run -P mysql    -Dspring-boot.run.profiles=mysql
mvn spring-boot:run -P postgres -Dspring-boot.run.profiles=postgres

```
