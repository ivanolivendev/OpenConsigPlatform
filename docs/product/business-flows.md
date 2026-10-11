# Fluxos de negócio

## Fluxo atual: simulação interna

```text
Cliente informa CPF, renda, valor e prazo
        ↓
API valida os dados básicos
        ↓
Domínio calcula a parcela com juros de 1,79% ao mês
        ↓
Domínio compara a parcela com 30% da renda
        ↓
Simulação recebe AVAILABLE ou UNAVAILABLE
        ↓
Resultado é persistido em PostgreSQL
        ↓
API retorna o resultado ao consumidor
```

O resultado `AVAILABLE` indica apenas que a parcela cabe no limite definido para a simulação. Não representa aprovação, contratação ou disponibilidade real de recursos.

## Fluxo futuro: proposta

```text
Simulação disponível
        ↓
Cliente ou parceiro solicita proposta
        ↓
Credit valida elegibilidade
        ↓
Risk avalia regras adicionais
        ↓
Proposta é criada com prazo de validade
```

## Fluxo futuro: contratação

```text
Proposta aceita
        ↓
Documentos e dados são validados
        ↓
Contrato é gerado
        ↓
Cliente assina
        ↓
Integração bancária realiza o desembolso
```

Propostas, contratos e desembolso ainda não fazem parte da implementação atual.
