import { createFileRoute, Link } from "@tanstack/react-router";
import { useQuery } from "@tanstack/react-query";
import { policies as policiesApi } from "@/lib/triminds-client";
import { Button } from "@/components/ui/button";
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
export const Route = createFileRoute("/_authenticated/policies")({
  head: () => ({ meta: [{ title: "Policies — Triminds Admin Console" }] }),
  component: PoliciesPage,
});
function PoliciesPage() {
  const list = useQuery({
    queryKey: ["admin", "policies"],
    queryFn: () => policiesApi.list(),
  });
  return (
    <Card>
      <CardHeader className="flex flex-row items-center justify-between">
        <div>
          <CardTitle>Políticas (OPA)</CardTitle>
          <CardDescription>
            Documentos Rego mantidos pelo Policy Engine.
          </CardDescription>
        </div>
        <Button asChild>
          <Link to="/policies/new">Nova política</Link>
        </Button>
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
                <TableHead>Nome</TableHead>
                <TableHead>Versão</TableHead>
                <TableHead>Atualizado em</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {list.data.map((p) => (
                <TableRow key={p.id}>
                  <TableCell className="font-medium">{p.name}</TableCell>
                  <TableCell>{p.version ?? "—"}</TableCell>
                  <TableCell className="text-muted-foreground">
                    {p.updatedAt ?? "—"}
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        )}
      </CardContent>
    </Card>
  );
}