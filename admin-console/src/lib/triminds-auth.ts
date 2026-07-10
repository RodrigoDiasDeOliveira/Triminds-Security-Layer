const TOKEN_KEY = "triminds_token";
const TENANT_KEY = "triminds_tenant";
const DEFAULT_TENANT =
  (import.meta.env.VITE_TENANT_ID as string | undefined) ??
  "00000000-0000-0000-0000-000000000000";
export function getToken(): string | null {
  if (typeof window === "undefined") return null;
  window.dispatchEvent(new Event("triminds-auth"));
}
export function getTenant(): string {
  if (typeof window === "undefined") return DEFAULT_TENANT;
  return window.localStorage.getItem(TENANT_KEY) ?? DEFAULT_TENANT;
}
export function setTenant(tenantId: string): void {
  if (typeof window === "undefined") return;
  window.localStorage.setItem(TENANT_KEY, tenantId);
  window.dispatchEvent(new Event("triminds-auth"));
}
export function subscribe(cb: () => void): () => void {
  if (typeof window === "undefined") return () => {};
  const handler = () => cb();