# ADR-003 — Verificar limites com ArchUnit e Spring Modulith

- **Status:** aceito
- **Data:** 2026-10-11

## Decisão

Os limites dos módulos são declarados pelo Spring Modulith e verificados no build com Spring Modulith e ArchUnit.

## Consequências

Uma violação arquitetural falha os testes antes de chegar ao runtime ou à revisão manual.
