# OpenConsigPlatform

OpenConsigPlatform é uma plataforma aberta de crédito consignado construída para demonstrar engenharia de backend Java com limites arquiteturais claros, ambiente reproduzível e evolução incremental.

O projeto usa dados sintéticos e não representa uma instituição financeira nem executa operações financeiras reais.

A aplicação começa como um **monólito modular**: uma única aplicação implantável, módulos internos explícitos, transações locais e uma possível evolução futura para integrações e eventos quando houver necessidade real.

## Estado atual

A primeira funcionalidade de negócio é uma simulação interna de crédito consignado.

Já disponível:

- Java 25 e Spring Boot 3.5;
- PostgreSQL e migrations Flyway;
- ambiente completo executado pelo Docker Compose;
- healthcheck com Spring Boot Actuator;
- módulos `access`, `partners` e `credit`;
- limites arquiteturais com ArchUnit e Spring Modulith;
- simulação de crédito persistida no PostgreSQL;
- documentação OpenAPI/Swagger;
- collections de teste para Postman e Insomnia.

Ainda não fazem parte desta fase:

- autenticação e autorização;
- consulta real de CPF;
- integração bancária;
- contratação, proposta ou desembolso;
- multi-tenancy efetivo;
- análise de risco de produção.

## Arquitetura

O projeto utiliza um monólito modular com organização hexagonal dentro de cada módulo:

```text
OpenConsigPlatform
├── access/
│   ├── domain/
│   ├── application/
│   ├── infrastructure/
│   └── interfaces/
├── partners/
│   ├── domain/
│   ├── application/
│   ├── infrastructure/
│   └── interfaces/
└── credit/
    ├── domain/
    ├── application/
    ├── infrastructure/
    └── interfaces/
```

Consulte a [visão arquitetural](docs/architecture/overview.md) e os [limites dos módulos](docs/architecture/module-boundaries.md).

## Pré-requisito

Instale apenas o Docker Desktop. Java, Maven e PostgreSQL são executados dentro dos containers.

## Executar localmente

Suba a aplicação e o PostgreSQL:

```powershell
docker compose up --build
```

Verifique a aplicação:

```powershell
Invoke-RestMethod http://localhost:8080/actuator/health
```

O resultado esperado é `status: UP`.

Para parar os containers:

```powershell
docker compose down
```

Para parar e remover também os dados locais do PostgreSQL:

```powershell
docker compose down -v
```

Mais detalhes estão no [guia de setup](docs/development/setup.md).

## Swagger e API

Com a aplicação em execução, acesse:

- [Swagger UI](http://localhost:8080/swagger-ui.html);
- [OpenAPI JSON](http://localhost:8080/v3/api-docs).

Endpoint atual:

```http
POST /api/v1/credit/simulations
```

Exemplo:

```json
{
  "cpf": "12345678900",
  "monthlyIncome": 5000.00,
  "requestedAmount": 10000.00,
  "installments": 24
}
```

A simulação usa juros de 1,79% ao mês e considera `AVAILABLE` quando a parcela não ultrapassa 30% da renda mensal. O resultado é persistido na tabela `platform.credit_simulations`.

## Ferramentas de teste

Também estão disponíveis na raiz do projeto:

- [postman-collection.json](postman-collection.json);
- [insomnia-export.json](insomnia-export.json).

As collections cobrem healthcheck, simulação disponível e cenários inválidos.

## Testes e qualidade

O build Docker executa:

- regras ArchUnit;
- validação de módulos Spring Modulith;
- empacotamento da aplicação Spring Boot.

Execute o build completo com:

```powershell
docker compose up --build
```

Consulte a [estratégia de testes](docs/development/testing-strategy.md) para os critérios atuais e próximos incrementos.

## Documentação

A [biblioteca de documentação](docs/README.md) é o ponto de entrada para arquitetura, decisões, regras de negócio, setup, testes e roadmap.

| Assunto | Documento |
| --- | --- |
| Arquitetura | [Visão arquitetural](docs/architecture/overview.md) |
| Limites dos módulos | [Module boundaries](docs/architecture/module-boundaries.md) |
| Decisões arquiteturais | [ADRs](docs/architecture/adr/README.md) |
| Simulação de crédito | [Credit simulation](docs/product/credit-simulation.md) |
| Glossário | [Domain glossary](docs/product/domain-glossary.md) |
| Setup local | [Development setup](docs/development/setup.md) |
| Estratégia de testes | [Testing strategy](docs/development/testing-strategy.md) |
| Roadmap | [Roadmap](docs/architecture/roadmap.md) |
| Status | [Project status](docs/status.md) |

## Roadmap

1. **Simulação interna:** regras financeiras básicas, persistência, API e documentação.
2. **Evolução do crédito:** histórico, elegibilidade, propostas e análise de crédito.
3. **Contratação:** contratos, desembolso e integrações externas.
4. **Resiliência e operação:** idempotência, auditoria, outbox, observabilidade e recuperação.
5. **IA controlada:** adaptador de risco, políticas, explicabilidade e aprovação humana.

Veja o [roadmap detalhado](docs/architecture/roadmap.md) e o [status atual](docs/status.md).

## Contribuição e Git Flow

O projeto usa branches de feature e mensagens em inglês seguindo Conventional Commits:

```text
main -> código integrado e estável
feature/* -> novas funcionalidades
fix/* -> correções
docs/* -> documentação
```

Fluxo recomendado:

```text
main
  ↓
feature/nome-da-tarefa
  ↓
desenvolvimento e testes
  ↓
Pull Request
  ↓
revisão
  ↓
merge na main
```

Consulte o [guia de Git Flow](docs/development/git-flow.md).
