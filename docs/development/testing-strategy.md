# Estratégia de testes

## Testes arquiteturais

- `ArchitectureRulesTest` verifica dependências proibidas entre camadas e módulos.
- `ModularityTest` verifica os limites declarados pelo Spring Modulith.

## Validação do ambiente

O Dockerfile executa os testes arquiteturais durante o build da imagem. O compose é validado com o healthcheck da aplicação e do PostgreSQL.

## Testes de API

Use o Swagger UI, o arquivo `postman-collection.json` ou `insomnia-export.json` para testar:

- simulação disponível;
- simulação indisponível por limite de renda;
- CPF inválido;
- renda ou valor inválidos;
- prazo fora do intervalo permitido.

## Próximo incremento

Adicionar testes unitários isolados para as regras do `CreditSimulationService` antes de ampliar o fluxo para propostas.
