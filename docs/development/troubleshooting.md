# Troubleshooting

## A porta 8080 já está em uso

Verifique qual processo está usando a porta:

```powershell
Get-NetTCPConnection -LocalPort 8080 -ErrorAction SilentlyContinue
```

Inicie usando outra porta:

```powershell
$env:SERVER_PORT = "8081"
docker compose up --build
```

Nesse caso, use `http://localhost:8081` para a aplicação.

## O PostgreSQL não fica saudável

Veja os containers:

```powershell
docker compose ps
```

Consulte os logs:

```powershell
docker compose logs postgres
```

## A aplicação não inicia

Consulte os logs do serviço:

```powershell
docker compose logs app
```

Confirme que o PostgreSQL está saudável antes de iniciar a aplicação.

## Migration falhou

Não edite uma migration já aplicada. Crie uma nova migration versionada, por exemplo `V3__description.sql`.

Para recriar apenas o ambiente local e os dados de desenvolvimento:

```powershell
docker compose down -v
docker compose up --build
```

O comando `down -v` remove os dados locais do PostgreSQL.

## Swagger não abre

Confirme que a aplicação está em execução e teste:

```powershell
Invoke-WebRequest http://localhost:8080/swagger-ui.html -UseBasicParsing
Invoke-RestMethod http://localhost:8080/v3/api-docs
```

O Swagger atual é local. O GitHub contém o código, mas não hospeda a aplicação em execução.

## A simulação retorna `UNAVAILABLE`

Isso não é erro técnico. A parcela calculada ultrapassou 30% da renda mensal informada. Consulte as regras em [credit-simulation.md](../product/credit-simulation.md).
