# Visão arquitetural

O OpenConsigPlatform começa como um monólito modular: uma única aplicação implantável, com módulos internos explícitos e dependências controladas.

Essa escolha mantém o desenvolvimento local simples, permite transações diretas com PostgreSQL e deixa uma possível extração futura baseada em limites reais, sem introduzir a complexidade operacional de microsserviços antes da necessidade.

## Módulos

```text
OpenConsigPlatform
├── access      autenticação, autorização e contexto de acesso — futuro
├── partners    parceiros, aplicações e relacionamentos — futuro
└── credit      simulações e evolução do ciclo de crédito
```

O módulo `credit` possui atualmente a simulação interna. Propostas, análise de risco, contratos e desembolso ainda não fazem parte do produto executável.

## Organização interna

Cada módulo segue uma estrutura hexagonal:

```text
module/
├── domain/          regras, entidades e invariantes
├── application/     casos de uso e portas
├── infrastructure/ persistência e adaptadores
└── interfaces/      REST, DTOs e entradas externas
```

O domínio não depende de Spring, HTTP ou banco de dados. A aplicação coordena os casos de uso através de portas; a infraestrutura implementa essas portas; as interfaces traduzem HTTP para comandos da aplicação.

## Princípios

1. Regras de negócio pertencem ao domínio.
2. Controllers não calculam regras financeiras.
3. Módulos não acessam classes internas de outros módulos.
4. ArchUnit e Spring Modulith verificam os limites automaticamente.
5. A complexidade só deve ser adicionada quando houver uma necessidade demonstrada.
