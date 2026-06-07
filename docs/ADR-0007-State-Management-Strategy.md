# ADR-0007: State Management Strategy

## Status
Proposed

## Context
The admin console currently uses local component state. For the console to scale, some shared state should be centralized.

## Decision
Use a hybrid state strategy:

- local page/component state for forms and transient UI behavior
- shared state via React context/providers for authentication status, theme, and tenant metadata
- consider a lightweight state library only if complexity grows

## Consequences
- better separation between transient UI state and shared application state
- easier handling of authenticated routes and tenant context
- lower complexity than a full global state solution initially
