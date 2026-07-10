import { createFileRoute } from "@tanstack/react-router";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { useState } from "react";
import { roles as rolesApi } from "@/lib/triminds-client";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Alert, AlertDescription } from "@/components/ui/alert";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { toast } from "sonner";
export const Route = createFileRoute("/_authenticated/roles")({
  head: () => ({ meta: [{ title: "Roles — Triminds Admin Console" }] }),
  component: RolesPage,
});
function RolesPage() {
  const [identityId, setIdentityId] = useState("");
  const [roleId, setRoleId] = useState("");
  const qc = useQueryClient();
  const list = useQuery({
    queryKey: ["admin", "roles"],
    queryFn: () => rolesApi.list(),
  });
  const assign = useMutation({
    mutationFn: () => rolesApi.assign(identityId, roleId),
    onSuccess: () => {
      toast.success("Papel atribuído");
      qc.invalidateQueries({ queryKey: ["admin"] });
    },
    onError: (e) => toast.error(String(e)),
  });
  const revoke = useMutation({
    mutationFn: () => rolesApi.revoke(identityId, roleId),
    onSuccess: () => {
      toast.success("Papel revogado");
      qc.invalidateQueries({ queryKey: ["admin"] });
    },
    onError: (e) => toast.error(String(e)),
  });
  return (
    <div className="space-y-6">
      <Card>
        <CardHeader>
          <CardTitle>Atribuição de papéis</CardTitle>
          <CardDescription>
            Atribua ou revogue papéis de uma identidade.
          </CardDescription>
        </CardHeader>
        <CardContent className="space-y-4">
          <div className="grid gap-4 sm:grid-cols-2">
            <div className="space-y-2">
              <Label htmlFor="identityId">Identity ID</Label>
              <Input
                id="identityId"
                value={identityId}
                onChange={(e) => setIdentityId(e.target.value)}
                placeholder="uuid do usuário"
              />
            </div>
            <div className="space-y-2">
              <Label htmlFor="roleId">Role ID</Label>
              <Input
                id="roleId"
                value={roleId}
                onChange={(e) => setRoleId(e.target.value)}
                placeholder="ex.: admin"
              />
            </div>
          </div>
          <div className="flex gap-2">
            <Button
              disabled={!identityId || !roleId || assign.isPending}
              onClick={() => assign.mutate()}
            >
              {assign.isPending ? "Atribuindo..." : "Assign"}
            </Button>
            <Button
              variant="outline"
              disabled={!identityId || !roleId || revoke.isPending}
              onClick={() => revoke.mutate()}
            >
              {revoke.isPending ? "Revogando..." : "Revoke"}
            </Button>
          </div>
        </CardContent>
      </Card>
      <Card>
        <CardHeader>
          <CardTitle>Papéis disponíveis</CardTitle>
        </CardHeader>
        <CardContent>
          {list.isLoading && <p className="text-sm text-muted-foreground">Carregando…</p>}
          {list.error && (
            <Alert variant="destructive">
              <AlertDescription>{String(list.error)}</AlertDescription>
            </Alert>
          )}
          {list.data && (
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>ID</TableHead>
                  <TableHead>Nome</TableHead>
                  <TableHead>Descrição</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {list.data.map((r) => (
                  <TableRow key={r.id}>
                    <TableCell className="font-mono text-xs">{r.id}</TableCell>
                    <TableCell>{r.name}</TableCell>
                    <TableCell className="text-muted-foreground">
                      {r.description ?? "—"}
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          )}
        </CardContent>
      </Card>
    </div>
  );
}