# Limites dos módulos

## `access`

Responsável futuramente por autenticação, autorização, escopos e contexto do parceiro. Nesta fase o módulo existe apenas como fronteira arquitetural; a API de simulação não exige autenticação.

## `partners`

Responsável futuramente pelo cadastro e relacionamento de parceiros, aplicações, agentes e tenants. Não participa da simulação interna atual.

## `credit`

Responsável pelas regras do ciclo de crédito. Atualmente expõe a simulação interna e persiste seus resultados. No futuro poderá evoluir para elegibilidade, propostas, análise, contratos e desembolso.

## Regras de dependência

- `domain` não depende de `infrastructure`.
- `domain` não depende de Spring.
- `application` não depende de `interfaces`.
- Um módulo não importa a infraestrutura interna de outro módulo.
- Contratos públicos devem ser pequenos e estáveis.

As regras são verificadas em `ArchitectureRulesTest` e `ModularityTest`.
