export interface Identity {
  id: string;
  username: string;
  email?: string;
  displayName?: string;
  status?: "active" | "disabled" | "locked";
  createdAt?: string;
}
export interface Role {
  id: string;
  name: string;
  description?: string;
}
export interface RoleAssignment {
  identityId: string;
  roleId: string;
  assignedAt?: string;
}
export interface PolicyDoc {
  id: string;
  name: string;
  rego: string;
  version?: number;
  updatedAt?: string;
}
export interface PolicyResult {
  allow: boolean;
  reasons: string[];
  obligations: string[];
}
export interface RiskSignal {
  id: string;
  subject: string;
  score: number;
  severity: "low" | "medium" | "high" | "critical";
  reason?: string;
  observedAt: string;
}
export interface AuditEvent {
  id: string;
  timestamp: string;
  type: string;
  actor?: string;
  resource?: string;
  outcome?: string;
  data?: Record<string, unknown>;
}
export interface IntelIOC {
  id: string;
  type: "ip" | "domain" | "hash" | "url" | string;
  value: string;
  source?: string;
  firstSeen?: string;
  lastSeen?: string;
  confidence?: number;
}
export interface IntelFeed {
  id: string;
  name: string;
  provider?: string;
  lastSyncAt?: string;
  itemCount?: number;
}
export interface ModuleHealth {
  name: string;
  status: "UP" | "DOWN" | "UNKNOWN";
  latencyMs?: number;
}
export interface Paginated<T> {
  items: T[];
  page: number;
  size: number;
  total: number;
}