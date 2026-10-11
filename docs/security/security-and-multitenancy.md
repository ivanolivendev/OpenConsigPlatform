# Segurança e multi-tenancy

## Estado atual

A API de simulação é interna e não exige autenticação. O CPF informado é validado apenas pelo formato de 11 dígitos; não existe consulta à Receita Federal ou a um cadastro externo.

Por esse motivo, o ambiente atual não deve ser exposto publicamente como uma API de produção.

## Access

O módulo `access` foi criado como fronteira arquitetural para tratar futuramente:

- autenticação;
- autorização;
- scopes e papéis;
- contexto do parceiro;
- identidade do operador ou agente.

O domínio de `credit` não deve ler diretamente estado de framework de segurança. A aplicação deverá receber um contrato explícito de contexto autorizado.

## Multi-tenancy futuro

O isolamento por tenant ainda não está implementado. Antes de ativá-lo, será necessário definir:

- quem é o tenant e quem é o parceiro;
- como o tenant é identificado;
- como o contexto chega ao caso de uso;
- como consultas e migrations preservam o isolamento;
- como administradores operam entre tenants;
- como auditoria registra acesso entre contextos.

Adicionar apenas uma coluna `tenant_id` não é suficiente: o isolamento precisa ser aplicado nos contratos, serviços, persistência, testes e observabilidade.

## Próximos requisitos antes de exposição externa

- autenticação e autorização;
- HTTPS;
- validação de entrada padronizada;
- rate limiting;
- logs sem exposição de dados sensíveis;
- segregação de credenciais;
- auditoria;
- política de retenção e proteção de dados.
