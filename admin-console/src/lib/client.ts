import { TrimindsClient } from "@triminds/sdks";

export const client = new TrimindsClient({
  baseUrl: import.meta.env.VITE_GATEWAY_URL ?? "http://localhost:8080",
  tenantId:
    import.meta.env.VITE_TENANT_ID ??
    "00000000-0000-0000-0000-000000000000",
  getToken: () => localStorage.getItem("triminds_token") ?? "",
});