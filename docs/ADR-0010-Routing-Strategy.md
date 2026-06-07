# ADR-0010: Routing Strategy

## Status
Proposed

## Context
The admin console already uses React Router in `admin-console/src/App.tsx` for navigation between pages.

## Decision
Continue using React Router for the Admin Console, with:

- nested routes for hierarchical sections
- route guards for authenticated or tenant-aware access
- route-based code splitting later if page complexity increases
- query parameters for filters and pagination

## Consequences
- consistent client-side navigation
- simple support for deep linking and bookmarked pages
- allows future page expansion without rewiring the router
