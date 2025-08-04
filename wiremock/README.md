# WireMock для имитации Old System API

Этот проект содержит конфигурацию WireMock для имитации Old System API с готовыми тестовыми данными.

## запуск WireMock

### Вариант 1: Docker Compose
docker-compose up -d

### Вариант 2: Custom Dockerfile
docker build -t clinic-wiremock .
docker run -d -p 8081:8081 clinic-wiremock


## API Endpoints

### Получение всех клиентов
POST http://localhost:8081/clients


### Получение заметок клиента
POST http://localhost:8081/notes