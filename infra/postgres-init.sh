#!/bin/bash
set -e
for db in identity auth access_control policy audit risk; do
  psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE DATABASE $db;
EOSQL
done
