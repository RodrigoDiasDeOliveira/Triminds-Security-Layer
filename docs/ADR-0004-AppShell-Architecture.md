# ADR-0004: AppShell Architecture

## Status
Proposed

## Context
The admin console currently implements a sidebar-based AppShell in `admin-console/src/App.tsx` and this layout should be formalized.

## Decision
Keep the AppShell pattern with:

- a persistent side navigation panel
- a main content area for pages
- a top heading region for page title and actions
- a consistent page wrapper for content sections

This architecture supports dashboard, user management and policy pages uniformly.

## Consequences
- consistent navigation and layout across the console
- easier support for future layout elements like drawers or notices
- improves the foundation for a shared UI library
