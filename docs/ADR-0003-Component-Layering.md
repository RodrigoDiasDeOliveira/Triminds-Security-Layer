# ADR-0003: Component Layering

## Status
Proposed

## Context
The current admin console scaffold includes pages and a simple AppShell, but it needs a clearer component hierarchy for long-term maintainability.

## Decision
Layer the frontend UI into:

- primitives: buttons, inputs, typography and layout helpers
- base components: cards, panels, tables and forms
- feature components: dashboard widgets, user management, policy editor, risk displays

This layering will support cleaner component reuse and a path to a shared Triminds UI library.

## Consequences
- clearer separation of UI responsibilities
- improved reuse and testing of components
- better structure for implementing the design system
