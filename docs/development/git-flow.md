# Git Flow do projeto

As mensagens de commit são sempre em inglês e seguem Conventional Commits.

## Branches

- `main`: código integrado e pronto para publicação.
- `feature/<name>`: nova funcionalidade.
- `fix/<name>`: correção de defeito.
- `docs/<name>`: documentação.

## Fluxo

```text
main
  ↓
feature/elias
  ↓
commits em inglês
  ↓
Pull Request
  ↓
revisão e validação
  ↓
merge na main
```

Antes de abrir um Pull Request, confirme que o build Docker e os testes arquiteturais passaram.
