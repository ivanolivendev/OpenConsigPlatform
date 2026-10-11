# ADR-002 — Usar PostgreSQL e Flyway

- **Status:** aceito
- **Data:** 2026-10-11

## Decisão

O PostgreSQL roda em container e as alterações de schema são versionadas com Flyway.

## Consequências

O ambiente é reproduzível e as migrations fazem parte do histórico do código. Dados locais podem ser removidos com `docker compose down -v`.
