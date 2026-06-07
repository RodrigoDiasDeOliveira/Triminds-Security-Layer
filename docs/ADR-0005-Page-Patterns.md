# ADR-0005: Page Patterns

## Status
Proposed

## Context
The project already has basic pages for dashboard, users and policies. A standard page pattern will help scale the console.

## Decision
Use standard page patterns for:

- dashboards with summary cards and contextual actions
- list pages with filters, tables and batch actions
- detail pages with side panels and detail views
- form pages for creating/editing entities

This pattern gives `Dashboard`, `Users`, and `Policies` pages a shared structure.

## Consequences
- faster implementation of new admin console pages
- consistent UX across domain areas
- easier reuse of page-level shells and sections
