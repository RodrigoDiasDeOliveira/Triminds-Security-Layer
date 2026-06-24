#!/bin/bash
# Cria múltiplos databases a partir de POSTGRES_MULTIPLE_DATABASES (csv).
# Fallback: lista padrão do projeto.
set -euo pipefail

DBS="${POSTGRES_MULTIPLE_DATABASES:-identity,auth,access_control,policy,audit,risk}"
OWNER="${POSTGRES_USER:-triminds}"

IFS=',' read -ra LIST <<< "$DBS"
for db in "${LIST[@]}"; do
  db="$(echo "$db" | xargs)" # trim
  [ -z "$db" ] && continue
  echo ">> creating database '$db' owned by '$OWNER'"
  psql -v ON_ERROR_STOP=1 --username "$OWNER" --dbname "postgres" <<-EOSQL
    SELECT 'CREATE DATABASE "$db"'
    WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = '$db')\gexec
    GRANT ALL PRIVILEGES ON DATABASE "$db" TO "$OWNER";
EOSQL
done
