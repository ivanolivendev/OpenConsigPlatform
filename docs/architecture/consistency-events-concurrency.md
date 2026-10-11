# Consistência, eventos e concorrência

## Consistência atual

A simulação é uma operação síncrona: calcula o resultado, persiste no PostgreSQL e retorna a resposta. A transação local é suficiente para o escopo atual.

## Eventos

Eventos não são necessários para a simulação atual. Quando o fluxo tiver propostas, contratos ou notificações, eventos poderão comunicar fatos confirmados, como:

- `CreditSimulationCreated`;
- `ProposalCreated`;
- `ProposalAccepted`;
- `ContractSigned`.

Eventos não devem esconder uma dependência obrigatória que precisa ser concluída antes da resposta HTTP.

## Concorrência futura

Reserva de margem e contratação poderão sofrer concorrência entre solicitações simultâneas. Antes da implementação, será necessário comparar:

- controle otimista;
- bloqueio pessimista curto;
- condição transacional diretamente no SQL.

Uma constraint no banco protege um invariante, mas sozinha não resolve todo o fluxo de concorrência, retry ou expiração.

## Outbox

O padrão transactional outbox fica adiado até existir um consumidor externo que exija publicação confiável. Antes disso, eventos internos podem permanecer síncronos e em processo.
