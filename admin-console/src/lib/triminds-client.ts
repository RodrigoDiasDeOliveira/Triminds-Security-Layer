// Cliente HTTP para o Triminds Security Gateway.
// Substitui o SDK @triminds/security-sdk usando apenas fetch (sem axios).
export interface TrimindsConfig {
  baseUrl: string;
  tenantId: string;
  getToken: () => string | Promise<string>;
// Cliente HTTP para o Triminds Security Gateway (BFF /admin/*).
// Todas as chamadas passam pelo gateway. O gateway roteia para
// os módulos (identity, access-control, policy-engine, risk-engine,
// audit, intelligence, auth).
import {
  clearToken,
  getTenant,
  getToken,
} from "@/lib/triminds-auth";
import type {
  AuditEvent,
  Identity,
  IntelFeed,
  IntelIOC,
  ModuleHealth,
  Paginated,
  PolicyDoc,
  PolicyResult,
  RiskSignal,
  Role,
  RoleAssignment,
} from "@/types/triminds";
const BASE_URL = (
  (import.meta.env.VITE_GATEWAY_URL as string | undefined) ??
  "http://localhost:8080"
).replace(/\/?$/, "/");
export class TrimindsHttpError extends Error {
  constructor(public status: number, message: string, public body?: string) {
    super(message);
  }
}
export interface CheckPermissionInput {
  identityId: string;
  action: string;
  resource: string;
type Query = Record<string, string | number | boolean | undefined | null>;
async function request<T>(
  path: string,
  init: RequestInit & { query?: Query; auth?: boolean } = {},
): Promise<T> {
  const { query, auth = true, headers, ...rest } = init;
  const url = new URL(path.replace(/^\//, ""), BASE_URL);
  if (query) {
    for (const [k, v] of Object.entries(query)) {
      if (v !== undefined && v !== null) url.searchParams.set(k, String(v));
    }
  }
  const finalHeaders: Record<string, string> = {
    "Content-Type": "application/json",
    "X-Tenant-Id": getTenant(),
    ...(headers as Record<string, string> | undefined),
  };
  if (auth) {
    const token = getToken();
    if (token) finalHeaders["Authorization"] = `Bearer ${token}`;
  }
  const res = await fetch(url.toString(), { ...rest, headers: finalHeaders });
  if (res.status === 401 && auth) {
    clearToken();
  }
  if (!res.ok) {
    const body = await res.text().catch(() => "");
    throw new TrimindsHttpError(
      res.status,
      `Triminds ${res.status} ${res.statusText}${body ? `: ${body}` : ""}`,
      body,
    );
  }
  if (res.status === 204) return undefined as T;
  const text = await res.text();
  return (text ? JSON.parse(text) : undefined) as T;
}
export interface AuditQuery {
  from: string;
  to: string;
  type?: string;
  page?: number;
  size?: number;
}
export interface PolicyResult {
  allow: boolean;
  reasons: string[];
  obligations: string[];
}
export class TrimindsClient {
  constructor(private cfg: TrimindsConfig) {}
  private async request<T>(
    path: string,
    init: RequestInit & { query?: Record<string, unknown> | object } = {},
  ): Promise<T> {
    const token = await this.cfg.getToken();
    const url = new URL(path.replace(/^\//, ""), this.cfg.baseUrl.replace(/\/?$/, "/"));
    if (init.query) {
      for (const [k, v] of Object.entries(init.query as Record<string, unknown>)) {
        if (v !== undefined && v !== null) url.searchParams.set(k, String(v));
      }
    }
    const res = await fetch(url.toString(), {
      ...init,
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
        "X-Tenant-Id": this.cfg.tenantId,
        ...(init.headers ?? {}),
      },
    });
    if (!res.ok) {
      const body = await res.text().catch(() => "");
      throw new Error(`Triminds ${res.status} ${res.statusText}: ${body}`);
    }
    if (res.status === 204) return undefined as T;
    return (await res.json()) as T;
  }
  checkPermission(i: CheckPermissionInput): Promise<boolean> {
    return this.request<{ allowed: boolean }>("/access/check", { query: i }).then(
      (d) => !!d.allowed,
    );
  }
  assignRole(identityId: string, roleId: string): Promise<void> {
    return this.request<void>("/access/roles/assign", {
      method: "POST",
      body: JSON.stringify({ identityId, roleId }),
    });
  }
  revokeRole(identityId: string, roleId: string): Promise<void> {
    return this.request<void>("/access/roles/revoke", {
      method: "POST",
      body: JSON.stringify({ identityId, roleId }),
    });
  }
  evaluatePolicy(input: Record<string, unknown>): Promise<PolicyResult> {
    return this.request<PolicyResult>("/policy/evaluate", {
      method: "POST",
      body: JSON.stringify({ input }),
    });
  }
  listAudit(params: AuditQuery): Promise<unknown> {
    return this.request<unknown>("/audit", { query: params });
  }
  async login(
    username: string,
    password: string,
  ): Promise<{ token: string; expiresIn?: number }> {
    const url = new URL(
      "auth/login",
      this.cfg.baseUrl.replace(/\/?$/, "/"),
    );
    const res = await fetch(url.toString(), {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "X-Tenant-Id": this.cfg.tenantId,
      },
      body: JSON.stringify({ username, password }),
    });
    if (!res.ok) {
      const body = await res.text().catch(() => "");
      throw new Error(`Login falhou (${res.status}): ${body || res.statusText}`);
    }
    const data = (await res.json()) as {
// ---------- Auth ----------
export const auth = {
  async login(username: string, password: string) {
    const data = await request<{
      token?: string;
      accessToken?: string;
      access_token?: string;
      expiresIn?: number;
    };
    }>("/auth/login", {
      method: "POST",
      auth: false,
      body: JSON.stringify({ username, password }),
    });
    const token = data.token ?? data.accessToken ?? data.access_token;
    if (!token) throw new Error("Resposta do login sem token");
    return { token, expiresIn: data.expiresIn };
  }
}
export const trimindsClient = new TrimindsClient({
  baseUrl: import.meta.env.VITE_GATEWAY_URL ?? "http://localhost:8080",
  tenantId:
    import.meta.env.VITE_TENANT_ID ?? "00000000-0000-0000-0000-000000000000",
  getToken: () =>
    (typeof window !== "undefined" &&
      window.localStorage.getItem("triminds_token")) ||
    "",
});
  },
  rotateKeys: () => request<void>("/admin/auth/rotate-keys", { method: "POST" }),
  sessions: () =>
    request<Array<{ id: string; user: string; createdAt: string }>>(
      "/admin/auth/sessions",
    ),
};
// ---------- Identities ----------
export const identities = {
  list: (q?: { query?: string; page?: number; size?: number }) =>
    request<Paginated<Identity>>("/admin/identities", { query: q }),
  get: (id: string) => request<Identity>(`/admin/identities/${id}`),
  create: (input: Partial<Identity> & { username: string; password?: string }) =>
    request<Identity>("/admin/identities", {
      method: "POST",
      body: JSON.stringify(input),
    }),
  update: (id: string, input: Partial<Identity>) =>
    request<Identity>(`/admin/identities/${id}`, {
      method: "PATCH",
      body: JSON.stringify(input),
    }),
  remove: (id: string) =>
    request<void>(`/admin/identities/${id}`, { method: "DELETE" }),
  rolesOf: (id: string) =>
    request<RoleAssignment[]>(`/admin/identities/${id}/roles`),
};
// ---------- Roles ----------
export const roles = {
  list: () => request<Role[]>("/admin/roles"),
  assign: (identityId: string, roleId: string) =>
    request<void>("/admin/roles/assign", {
      method: "POST",
      body: JSON.stringify({ identityId, roleId }),
    }),
  revoke: (identityId: string, roleId: string) =>
    request<void>("/admin/roles/revoke", {
      method: "POST",
      body: JSON.stringify({ identityId, roleId }),
    }),
  check: (input: { identityId: string; action: string; resource: string }) =>
    request<{ allowed: boolean }>("/admin/roles/check", { query: input }).then(
      (d) => !!d.allowed,
    ),
};
// ---------- Policies ----------
export const policies = {
  list: () => request<PolicyDoc[]>("/admin/policies"),
  get: (id: string) => request<PolicyDoc>(`/admin/policies/${id}`),
  upsert: (doc: Pick<PolicyDoc, "id" | "name" | "rego">) =>
    request<PolicyDoc>("/admin/policies", {
      method: "POST",
      body: JSON.stringify(doc),
    }),
  remove: (id: string) =>
    request<void>(`/admin/policies/${id}`, { method: "DELETE" }),
  evaluate: (input: Record<string, unknown>) =>
    request<PolicyResult>("/admin/policies/evaluate", {
      method: "POST",
      body: JSON.stringify({ input }),
    }),
};
// ---------- Risk ----------
export const risk = {
  signals: (q?: { severity?: string; page?: number; size?: number }) =>
    request<Paginated<RiskSignal>>("/admin/risk/signals", { query: q }),
  score: (input: Record<string, unknown>) =>
    request<{ score: number; severity: string; reasons: string[] }>(
      "/admin/risk/score",
      { method: "POST", body: JSON.stringify(input) },
    ),
};
// ---------- Audit ----------
export const audit = {
  list: (params: {
    from: string;
    to: string;
    type?: string;
    page?: number;
    size?: number;
  }) => request<Paginated<AuditEvent>>("/admin/audit", { query: params }),
};
// ---------- Intelligence ----------
export const intel = {
  feeds: () => request<IntelFeed[]>("/admin/intel/feeds"),
  iocs: (q?: { type?: string; query?: string; page?: number; size?: number }) =>
    request<Paginated<IntelIOC>>("/admin/intel/iocs", { query: q }),
};
// ---------- Health ----------
export const health = {
  aggregate: () =>
    request<{ modules: ModuleHealth[] }>("/admin/health/aggregate", {
      auth: false,
    }),
};
// Objeto único conveniente
export const triminds = {
  auth,
  identities,
  roles,
  policies,
  risk,
  audit,
  intel,
  health,
};
// Compat: alguns lugares antigos ainda usam `trimindsClient`
export const trimindsClient = {
  login: auth.login,
  assignRole: roles.assign,
  revokeRole: roles.revoke,
  evaluatePolicy: policies.evaluate,
  listAudit: audit.list,
};