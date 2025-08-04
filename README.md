## Start DEV profile
### Only PostgreSQL:
docker compose --profile dev up --build

## Start PROD profile
### PostgreSQL and app:
sdocker compose --profile prod up --build

## Swagger URL
http://localhost:8080/swagger-ui/index.html