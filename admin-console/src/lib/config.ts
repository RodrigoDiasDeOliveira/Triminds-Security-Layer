// src/config.ts ou src/lib/config.ts
export const config = {
  gatewayUrl: import.meta.env.VITE_GATEWAY_URL || 'http://localhost:8080',
  apiBaseUrl: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080',
  authUrl: import.meta.env.VITE_AUTH_URL || 'http://localhost:8080/auth',
  jwkSetUri: import.meta.env.VITE_JWK_SET_URI || 'http://localhost:8080/.well-known/jwks.json',
  appName: import.meta.env.VITE_APP_NAME || 'Triminds Admin',
  debug: import.meta.env.VITE_DEBUG === 'true',
} as const;

export default config;