import { createFileRoute, Link } from "@tanstack/react-router";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import {
  identities as identitiesApi,
  roles as rolesApi,
} from "@/lib/triminds-client";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Alert, AlertDescription } from "@/components/ui/alert";
import { toast } from "sonner";
export const Route = createFileRoute("/_authenticated/identities/$id")({
  head: () => ({ meta: [{ title: "Identity — Triminds" }] }),
  component: IdentityDetail,
});
function IdentityDetail() {
  const { id } = Route.useParams();
  const qc = useQueryClient();
  const identity = useQuery({
    queryKey: ["admin", "identities", id],
    queryFn: () => identitiesApi.get(id),
  });
  const rolesQ = useQuery({
    queryKey: ["admin", "identities", id, "roles"],
    queryFn: () => identitiesApi.rolesOf(id),
  });
  const revoke = useMutation({
    mutationFn: (roleId: string) => rolesApi.revoke(id, roleId),
    onSuccess: () => {
      toast.success("Papel revogado");
      qc.invalidateQueries({ queryKey: ["admin", "identities", id] });
    },
    onError: (e) => toast.error(String(e)),
  });
  const remove = useMutation({
    mutationFn: () => identitiesApi.remove(id),
    onSuccess: () => toast.success("Identidade removida"),
    onError: (e) => toast.error(String(e)),
  });
  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <Button variant="ghost" asChild>
          <Link to="/identities">← Voltar</Link>
        </Button>
        <Button variant="destructive" onClick={() => remove.mutate()}>
          Remover identidade
        </Button>
      </div>
      <Card>
        <CardHeader>
          <CardTitle>{identity.data?.username ?? id}</CardTitle>
          <CardDescription>{identity.data?.email ?? "—"}</CardDescription>
        </CardHeader>
        <CardContent className="space-y-1 text-sm">
          <div><span className="font-medium">ID:</span> <code>{id}</code></div>
          <div><span className="font-medium">Status:</span> {identity.data?.status ?? "—"}</div>
          <div><span className="font-medium">Criada em:</span> {identity.data?.createdAt ?? "—"}</div>
          {identity.error && (
            <Alert variant="destructive">
              <AlertDescription>{String(identity.error)}</AlertDescription>
            </Alert>
          )}
        </CardContent>
      </Card>
      <Card>
        <CardHeader>
          <CardTitle>Papéis</CardTitle>
        </CardHeader>
        <CardContent>
          {rolesQ.isLoading && <p className="text-sm text-muted-foreground">Carregando…</p>}
          {rolesQ.error && (
            <Alert variant="destructive">
              <AlertDescription>{String(rolesQ.error)}</AlertDescription>
            </Alert>
          )}
          {rolesQ.data && (
            <ul className="divide-y">
              {rolesQ.data.map((r) => (
                <li key={r.roleId} className="flex items-center justify-between py-2">
                  <span className="font-mono text-sm">{r.roleId}</span>
                  <Button
                    size="sm"
                    variant="outline"
                    onClick={() => revoke.mutate(r.roleId)}
                  >
                    Revogar
                  </Button>
                </li>
              ))}
              {rolesQ.data.length === 0 && (
                <li className="py-2 text-sm text-muted-foreground">
                  Nenhum papel atribuído.
                </li>
              )}
            </ul>
          )}
        </CardContent>
      </Card>
    </div>
  );
}