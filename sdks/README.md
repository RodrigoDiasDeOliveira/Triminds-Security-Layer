# SDKs Triminds Security Layer

Este diretório define a estratégia dos SDKs multi-linguagem para adotar a plataforma em diferentes ecossistemas.

## Linguagens direcionadas

- `java/` - SDK Java com integração para Spring Boot e aplicativos corporativos.
- `typescript/` - SDK para frontend React/Node.js e aplicativos web.
- `python/` - SDK para automação, scripts e microserviços Python.
- `go/` - SDK para infraestrutura, agentes e microserviços em Go.

## Objetivos

- padronizar autenticação e validação de tokens JWT/OAuth2;
- permitir chamadas seguras ao Security Gateway;
- expor helpers de auditoria e eventos de segurança;
- facilitar integração com políticas OPA e enforcement;
- entregar exemplos práticos e documentados.

## Estrutura inicial

- `java/README.md`
- `typescript/README.md`

## Próximo passo

Definir o primeiro SDK de referência, preferencialmente `typescript/`, e depois gerar wrappers em Java, Python e Go.
