# ADR-004 — Começar com simulação interna

- **Status:** aceito
- **Data:** 2026-10-11

## Contexto

O objetivo inicial é validar o fluxo de crédito de forma visível pelo Postman, sem depender de autenticação, bancos externos ou contratação real.

## Decisão

O módulo `credit` começa com uma simulação interna, persistida no PostgreSQL, acessível por REST e sem autenticação.

## Fora do escopo atual

Não há aprovação definitiva, proposta, contrato, desembolso, consulta real de CPF ou integração bancária.
