# OpenConsigPlatform — documentação

Esta pasta é a fonte de documentação técnica e funcional do OpenConsigPlatform. Ela deve refletir o estado real do código e separar claramente o que está implementado do que está planejado.

## Caminhos de leitura

- **Novo desenvolvedor:** [setup e fluxo local](development/setup.md), [estratégia de testes](development/testing-strategy.md) e [Git Flow](development/git-flow.md).
- **Arquitetura:** [visão geral](architecture/overview.md), [limites dos módulos](architecture/module-boundaries.md) e [ADRs](architecture/adr/README.md).
- **Produto e domínio:** [simulação de crédito](product/credit-simulation.md) e [glossário](product/domain-glossary.md).
- **Planejamento:** [status atual](status.md) e [roadmap](architecture/roadmap.md).

## Mapa

```text
architecture/    decisões, módulos e evolução técnica
development/     setup, testes e workflow de contribuição
product/         regras e linguagem do negócio
status.md        implementado, próximo e adiado
```

## Estado atual

O sistema é um monólito modular executado em Docker. A primeira funcionalidade de negócio é a simulação interna de crédito consignado, sem autenticação, integração bancária ou contratação real.
