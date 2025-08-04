# Clinic Service
This project demonstrates service which runs scheduled tasks to import data from an old system to a new system.

## Start DEV profile
### Only PostgreSQL:
docker compose --profile dev up --build

## Start PROD profile
### PostgreSQL and app:
docker compose --profile prod up --build

## Swagger URL
Swagger added to project just for demonstration purposes.
No public API is available yet.

### Access Swagger UI
http://localhost:8080/swagger-ui/index.html


## WireMock for Mocking the Old System API
### Running WireMock
```
cd wiremock
docker-compose up -d
```
### API Endpoints

#### Get All Clients
```
POST http://localhost:8081/clients
```

#### Get Client Notes
```
POST http://localhost:8081/notes
```

## Running Tests
### Unit Tests
Run unit tests with:
```
./gradlew test
```