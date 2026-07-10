import axios from "axios";
export class TrimindsClient {
    constructor(cfg) {
        this.cfg = cfg;
        this.http = axios.create({ baseURL: cfg.baseUrl });
        this.http.interceptors.request.use(async (c) => {
            const t = await cfg.getToken();
            c.headers.Authorization = `Bearer ${t}`;
            c.headers["X-Tenant-Id"] = cfg.tenantId;
            return c;
        });
    }
    async checkPermission(i) {
        const { data } = await this.http.get("/access/check", { params: i });
        return !!data.allowed;
    }
    async assignRole(identityId, roleId) {
        await this.http.post("/access/roles/assign", { identityId, roleId });
    }
    async revokeRole(identityId, roleId) {
        await this.http.post("/access/roles/revoke", { identityId, roleId });
    }
    async evaluatePolicy(input) {
        const { data } = await this.http.post("/policy/evaluate", { input });
        return data;
    }
    async listAudit(params) {
        const { data } = await this.http.get("/audit", { params });
        return data;
    }
}
