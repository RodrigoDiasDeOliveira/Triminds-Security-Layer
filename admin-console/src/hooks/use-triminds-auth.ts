import { useSyncExternalStore } from "react";
import { getToken, subscribe } from "@/lib/triminds-auth";
import { getToken, getTenant, subscribe } from "@/lib/triminds-auth";
export function useTrimindsAuth(): { token: string | null; isAuthenticated: boolean } {
export function useTrimindsAuth(): {
  token: string | null;
  tenant: string;
  isAuthenticated: boolean;
} {
  const token = useSyncExternalStore(
    subscribe,
    () => getToken(),
    () => null,
  );
  return { token, isAuthenticated: !!token };
  const tenant = useSyncExternalStore(
    subscribe,
    () => getTenant(),
    () => getTenant(),
  );
  return { token, tenant, isAuthenticated: !!token };
}