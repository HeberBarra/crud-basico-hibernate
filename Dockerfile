FROM mysql:9.0.1

COPY ./src/main/resources/*.sql /docker-entrypoint-initdb.d/
COPY ./src/main/resources/*.sh  /docker-entrypoint-initdb.d/