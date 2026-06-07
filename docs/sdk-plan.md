# Plano de SDKs Multi-linguagem

## Visão geral

Os SDKs são peças-chave para tornar o Triminds Security Layer fácil de adotar em diferentes ecossistemas.

### Objetivos principais

- validar tokens e gerenciar sessões de forma consistente;
- oferecer integrações simples com o Security Gateway;
- padronizar chamadas de auditoria, logs e eventos de risco;
- suportar políticas dinâmicas e autorização adaptativa.

## Estratégia de rollout

1. `typescript/` como SDK de referência para front-end e Node.js.
2. `java/` para clientes corporativos e integração com Spring Boot.
3. `python/` para automação, data science e serviços serverless.
4. `go/` para infraestrutura, agentes e serviços de baixa latência.

## Módulos essenciais

- Auth client
- Gateway client
- Policy client
- Audit client
- Common models / DTOs

## Padrões e APIs esperadas

- suporte a JWT e OAuth2
- conexões seguras via HTTPS
- integração com OPA para políticas de decisão
- documentação de endpoints e exemplos de uso
