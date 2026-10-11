# Simulação interna de crédito

## Endpoint

```http
POST /api/v1/credit/simulations
```

## Entrada

```json
{
  "cpf": "12345678900",
  "monthlyIncome": 5000.00,
  "requestedAmount": 10000.00,
  "installments": 24
}
```

## Regras atuais

- CPF deve conter exatamente 11 dígitos.
- Renda mensal deve ser maior que zero.
- Valor solicitado deve ser maior que zero.
- Prazo deve estar entre 1 e 84 parcelas.
- Juros são fixos em 1,79% ao mês.
- A parcela é `AVAILABLE` quando não ultrapassa 30% da renda mensal.
- Caso ultrapasse esse limite, o resultado é `UNAVAILABLE`.

`AVAILABLE` não representa aprovação definitiva. É apenas o resultado da regra de capacidade da simulação.

## Fora do escopo

O CPF não é consultado externamente. Não há autenticação, análise bancária, proposta, contrato ou desembolso.
