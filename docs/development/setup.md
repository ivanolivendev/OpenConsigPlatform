# Setup de desenvolvimento

## Pré-requisito

Instale apenas o Docker Desktop. Java, Maven e PostgreSQL são executados dentro dos containers.

## Subir o ambiente

```powershell
docker compose up --build
```

## Verificar a aplicação

```powershell
Invoke-RestMethod http://localhost:8080/actuator/health
```

Swagger UI:

```text
http://localhost:8080/swagger-ui.html
```

## Parar

```powershell
docker compose down
```

Para remover também os dados locais do PostgreSQL:

```powershell
docker compose down -v
```
