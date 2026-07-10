routes/_authenticated.settings.tsx
import { createFileRoute } from "@tanstack/react-router";
import { useQuery } from "@tanstack/react-query";
import { useState } from "react";
import { auth as authApi, health as healthApi } from "@/lib/triminds-client";
import { getTenant, setTenant } from "@/lib/triminds-auth";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Alert, AlertDescription } from "@/components/ui/alert";
import { Badge } from "@/components/ui/badge";
import { toast } from "sonner";
export const Route = createFileRoute("/_authenticated/settings")({
  head: () => ({ meta: [{ title: "Settings — Triminds" }] }),
  component: SettingsPage,
});
function SettingsPage() {
  const [tenant, setTenantValue] = useState(getTenant());
  const [rotating, setRotating] = useState(false);
  const [rotErr, setRotErr] = useState<string | null>(null);
  const health = useQuery({
    queryKey: ["admin", "health"],
    queryFn: () => healthApi.aggregate(),
    refetchInterval: 15000,
  });
  const rotate = async () => {
    setRotErr(null); setRotating(true);
    try { await authApi.rotateKeys(); toast.success("Chaves rotacionadas"); }
    catch (e) { setRotErr(e instanceof Error ? e.message : String(e)); }
    finally { setRotating(false); }
  };
  return (
    <div className="space-y-6">
      <Card>
        <CardHeader><CardTitle>Tenant</CardTitle><CardDescription>Header <code>X-Tenant-Id</code>.</CardDescription></CardHeader>
        <CardContent className="space-y-3">
          <div className="space-y-2">
            <Label htmlFor="tenant">Tenant ID</Label>
            <Input id="tenant" value={tenant} onChange={(e) => setTenantValue(e.target.value)} />
          </div>
          <Button onClick={() => { setTenant(tenant); toast.success("Tenant atualizado"); }}>Salvar</Button>
        </CardContent>
      </Card>
      <Card>
        <CardHeader><CardTitle>Chaves de assinatura</CardTitle><CardDescription>Rotaciona chaves do <code>security-auth</code>.</CardDescription></CardHeader>
        <CardContent className="space-y-3">
          {rotErr && <Alert variant="destructive"><AlertDescription>{rotErr}</AlertDescription></Alert>}
          <Button onClick={rotate} disabled={rotating}>{rotating ? "Rotacionando…" : "Rotacionar agora"}</Button>
        </CardContent>
      </Card>
      <Card>
        <CardHeader><CardTitle>Saúde dos módulos</CardTitle><CardDescription>Atualiza a cada 15s.</CardDescription></CardHeader>
        <CardContent>
          {health.error && <Alert variant="destructive"><AlertDescription>{String(health.error)}</AlertDescription></Alert>}
          {health.data && (
            <ul className="divide-y">
              {health.data.modules.map((m) => (
                <li key={m.name} className="flex items-center justify-between py-2 text-sm">
                  <span className="font-medium">{m.name}</span>
                  <div className="flex items-center gap-3">
                    {m.latencyMs !== undefined && <span className="text-xs text-muted-foreground">{m.latencyMs}ms</span>}
                    <Badge variant={m.status === "UP" ? "default" : m.status === "DOWN" ? "destructive" : "secondary"}>{m.status}</Badge>
                  </div>
                </li>
              ))}
            </ul>
          )}
        </CardContent>
      </Card>
    </div>
  );
}