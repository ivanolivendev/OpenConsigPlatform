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

## Documentação

Consulte a [biblioteca de documentação](docs/README.md) para entender a arquitetura, o fluxo da simulação de crédito, o desenvolvimento local, os testes e o roadmap.

Com a aplicação em execução, a documentação interativa da API está disponível no [Swagger UI](http://localhost:8080/swagger-ui.html).

## Parar

```powershell
docker compose down
```

Para remover também os dados locais do PostgreSQL:

```powershell
docker compose down -v
```
