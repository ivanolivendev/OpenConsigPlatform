# Roadmap

## Fase atual — simulação interna

- Executar toda a aplicação dentro do Docker.
- Manter módulos `access`, `partners` e `credit`.
- Validar limites com ArchUnit e Spring Modulith.
- Simular crédito sem autenticação, integração bancária ou contratação real.
- Documentar e testar a API pelo Swagger, Postman e Insomnia.

## Próximas evoluções

- Testes unitários das regras financeiras.
- Contrato de erro padronizado para a API.
- Consulta e histórico de simulações.
- Cadastro de parceiros e contexto de acesso.
- Propostas e análise de crédito.
- Contratos e integração bancária.

## Adiado até existir necessidade

- OAuth2/OIDC e RBAC de produção.
- Multi-tenancy efetivo.
- Idempotência HTTP.
- Reserva concorrente de margem.
- Outbox e mensageria externa.
- Observabilidade e recuperação operacional avançadas.
- Uso de IA em decisões financeiras.
