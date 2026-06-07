# ADR-0002: Theming Strategy

## Status
Proposed

## Context
The admin console currently uses a dark theme stylesheet and needs a more scalable theming strategy for future customization and branding.

## Decision
Use a theme-driven UI approach based on:

- CSS variables for colors, spacing, typography, and elevation
- a theme provider layer in React
- support for light and dark mode
- optional brand palettes for future customization

This strategy lets the current `admin-console` CSS be replaced by a theming layer over time.

## Consequences
- UI can support consistent branding across deployments
- easier adoption of accessibility-friendly palettes
- simplified theme switching and extension
