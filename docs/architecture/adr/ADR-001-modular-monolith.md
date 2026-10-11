# ADR-001 — Adotar monólito modular

- **Status:** aceito
- **Data:** 2026-10-11

## Contexto

O produto está no início e precisa permitir evolução rápida, testes end-to-end simples e execução local reproduzível.

## Decisão

Começar com um monólito modular: uma aplicação, vários módulos internos e limites verificados automaticamente.

## Consequências

Ganhamos simplicidade operacional e transações locais. Em troca, precisamos respeitar os limites internos e evitar dependências acidentais entre módulos.
