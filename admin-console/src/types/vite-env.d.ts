/// <reference types="vite/client" />

interface ImportMetaEnv {
  readonly VITE_GATEWAY_URL: string
  readonly VITE_API_BASE_URL: string
  readonly VITE_AUTH_URL: string
  readonly VITE_JWK_SET_URI: string
  readonly VITE_APP_NAME: string
  readonly VITE_DEBUG: string
  // Adicione aqui outras variáveis que criar
}

interface ImportMeta {
  readonly env: ImportMetaEnv
}