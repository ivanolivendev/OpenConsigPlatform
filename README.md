# OpenConsigPlatform

Fundação de uma plataforma aberta de crédito consignado. O projeto usa dados sintéticos e começa como um monólito modular.

## Pré-requisito

Instale apenas o Docker Desktop. Java, Maven e PostgreSQL rodam dentro dos containers.

## Executar

```powershell
docker compose up --build
```

Verifique a aplicação:

```powershell
Invoke-RestMethod http://localhost:8080/actuator/health
```

O resultado esperado é `status: UP`.

## Parar

```powershell
docker compose down
```

Para remover também os dados locais do PostgreSQL:

```powershell
docker compose down -v
```
