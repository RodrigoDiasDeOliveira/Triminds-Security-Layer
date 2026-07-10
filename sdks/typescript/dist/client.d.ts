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
export declare class TrimindsClient {
    private cfg;
    private http;
    constructor(cfg: TrimindsConfig);
    checkPermission(i: CheckPermissionInput): Promise<boolean>;
    assignRole(identityId: string, roleId: string): Promise<void>;
    revokeRole(identityId: string, roleId: string): Promise<void>;
    evaluatePolicy(input: Record<string, unknown>): Promise<{
        allow: boolean;
        reasons: string[];
        obligations: string[];
    }>;
    listAudit(params: {
        from: string;
        to: string;
        type?: string;
        page?: number;
        size?: number;
    }): Promise<any>;
}
