# Documentation

This folder contains architecture documentation and decision records for the Triminds Security Layer.

## Overview

The Triminds Security Layer is a modular security platform built as a Maven monorepo, with backend services in Spring Boot and a React-based Admin Console.

## Included ADRs

The repository defines architecture decisions for the frontend, backend, and platform structure:

- `ADR-0000-Index.md` - ADR navigation index
- `ADR-0001-Monorepo-Design-System.md` - Monorepo and shared design system strategy
- `ADR-0002-Theming-Strategy.md` - UI theming strategy for the admin console
- `ADR-0003-Component-Layering.md` - Component layering and reuse
- `ADR-0004-AppShell-Architecture.md` - Admin console shell and layout
- `ADR-0005-Page-Patterns.md` - Page patterns and UX consistency
- `ADR-0006-Package-Boundaries.md` - Package/module boundaries in the monorepo
- `ADR-0007-State-Management-Strategy.md` - State management approach for the console
- `ADR-0008-Data-Fetching-Layer.md` - Shared data fetching architecture
- `ADR-0009-Plugin-Architecture.md` - Future plugin extensibility strategy
- `ADR-0010-Routing-Strategy.md` - Client-side routing strategy
- `ADR-0011-Multi-Tenant-System.md` - Multi-tenant platform strategy

## Architecture summary

- Backend is split into dedicated Spring Boot modules for identity, auth, access control, policy, risk, gateway, intelligence, and audit.
- `shared` contains common models and utilities.
- `admin-console` is a React + Vite application with a sidebar AppShell and core page scaffolding.
- `sdks` is reserved for multi-language client SDKs across Java, TypeScript, Python, and Go.

## Usage

Use this docs folder as the source of truth for architectural decisions, design direction, and future platform evolution.
