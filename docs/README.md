# Triminds Security Layer

**🛡️ Intelligent Security Framework for Distributed Systems**

A modular **Identity & Security Platform** designed to support high-scale, secure, and compliant applications at Triminds.

---

## Overview

The Triminds Security Layer is a comprehensive and reusable security platform that centralizes identity, authorization, risk detection, and compliance for modern distributed systems.

### Four Core Pillars

1. **Identity and Access Management (IAM)**
2. **Application & API Security**
3. **Risk Monitoring & AI Detection**
4. **Governance & Compliance**

---

## Monorepo Structure

| Module                        | Responsibility |
|-------------------------------|----------------|
| `security-identity`           | Identity Provider (OAuth2 / OIDC) |
| `security-auth`               | Authentication, MFA, SSO, Tokens |
| `security-access-control`     | RBAC + ABAC + Access Policies |
| `security-policy-engine`      | Policy Engine (OPA) |
| `security-risk-engine`        | AI/ML Risk Engine |
| `security-gateway`            | Security API Gateway |
| `security-intelligence`       | Observability & Anomaly Detection |
| `security-audit`              | Immutable Audit Log |
| `admin-console`               | Management UI (React + Triminds UI) |
| `shared`                      | Shared models, events and utilities |
| `sdks`                        | Official SDKs (Java, TypeScript, Python, Go) |

---

## Tech Stack

- **Backend**: Java 21 + Spring Boot 3.4
- **Identity**: Spring Authorization Server
- **Policies**: Open Policy Agent (OPA)
- **Risk & AI**: LangChain4j
- **Database**: PostgreSQL + Redis
- **Observability**: OpenTelemetry + Grafana
- **Frontend**: React + Vite + Triminds UI Core

---

## Getting Started

```bash
# Clone the repository
git clone https://github.com/RodrigoDiasDeOliveira/Triminds-Security-Layer.git

# Build shared modules
mvn clean install -pl shared -am

# Run a service (example)
mvn spring-boot:run -pl security-identity