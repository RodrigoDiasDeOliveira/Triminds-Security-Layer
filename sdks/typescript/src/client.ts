import axios, { AxiosInstance } from "axios";

export interface TrimindsConfig {
  baseUrl: string;
  getToken: () => string | Promise<string>;
  tenantId: string;
}

export interface CheckPermissionInput {
  identityId: string;
  action: string;
  resource: string;
}

export class TrimindsClient {
  private http: AxiosInstance;
  constructor(private cfg: TrimindsConfig) {
    this.http = axios.create({ baseURL: cfg.baseUrl });
    this.http.interceptors.request.use(async (c) => {
      const t = await cfg.getToken();
      c.headers.Authorization = `Bearer ${t}`;
      c.headers["X-Tenant-Id"] = cfg.tenantId;
      return c;
    });
  }

  async checkPermission(i: CheckPermissionInput): Promise<boolean> {
    const { data } = await this.http.get("/access/check", { params: i });
    return !!data.allowed;
  }

  async assignRole(identityId: string, roleId: string) {
    await this.http.post("/access/roles/assign", { identityId, roleId });
  }

  async revokeRole(identityId: string, roleId: string) {
    await this.http.post("/access/roles/revoke", { identityId, roleId });
  }

  async evaluatePolicy(input: Record<string, unknown>) {
    const { data } = await this.http.post("/policy/evaluate", { input });
    return data as { allow: boolean; reasons: string[]; obligations: string[] };
  }

  async listAudit(params: { from: string; to: string; type?: string; page?: number; size?: number }) {
    const { data } = await this.http.get("/audit", { params });
    return data;
  }
}
