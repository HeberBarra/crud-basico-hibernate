#!/bin/bash
set -e

mysql --protocol=socket -uroot -p"$MYSQL_ROOT_PASSWORD" <<EOSQL
CREATE USER IF NOT EXISTS 'usuario'@'%' IDENTIFIED BY '$SENHA_USUARIO';
GRANT INSERT, SELECT, UPDATE, DELETE ON db_crud.* TO 'usuario'@'%';
FLUSH PRIVILEGES;
EOSQL
