# Escopo da fase 1

## Entrega da fase

A fase 1 prova que a plataforma consegue executar, validar limites arquiteturais e entregar uma primeira regra de negócio observável.

## Incluído

- Spring Boot executando dentro do Docker;
- PostgreSQL e Flyway;
- healthcheck;
- monólito modular com `access`, `partners` e `credit`;
- ArchUnit e Spring Modulith;
- simulação interna de crédito;
- persistência do resultado;
- endpoint REST versionado;
- Swagger/OpenAPI;
- collections Postman e Insomnia;
- documentação de arquitetura e desenvolvimento.

## Critérios de aceite

- `docker compose up --build` inicia a aplicação e o banco;
- healthcheck retorna `UP`;
- testes arquiteturais passam no build da imagem;
- uma simulação válida retorna `201`;
- entradas inválidas retornam `400`;
- simulações são persistidas em `platform.credit_simulations`;
- a documentação descreve o comportamento real.

## Fora da fase 1

- autenticação e autorização;
- multi-tenancy efetivo;
- propostas, contratos e desembolso;
- consulta externa de CPF;
- integração bancária;
- reserva de margem concorrente;
- mensageria externa;
- IA em decisões de crédito;
- publicação pública da API.
