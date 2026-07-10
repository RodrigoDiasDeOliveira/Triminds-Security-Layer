import { createFileRoute } from "@tanstack/react-router";
import { useQuery } from "@tanstack/react-query";
import { audit as auditApi } from "@/lib/triminds-client";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Alert, AlertDescription } from "@/components/ui/alert";
import { Skeleton } from "@/components/ui/skeleton";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
export const Route = createFileRoute("/_authenticated/audit")({
  head: () => ({ meta: [{ title: "Audit — Triminds Admin Console" }] }),
  component: AuditPage,
});
function AuditPage() {
  const to = new Date().toISOString();
  const from = new Date(Date.now() - 24 * 3600 * 1000).toISOString();
  const q = useQuery({
    queryKey: ["admin", "audit", from, to],
    queryFn: () => auditApi.list({ from, to, page: 0, size: 100 }),
  });
  return (
    <Card>
      <CardHeader>
        <CardTitle>Trilha de auditoria</CardTitle>
        <CardDescription>Últimas 24h.</CardDescription>
      </CardHeader>
      <CardContent>
        {q.isLoading && <Skeleton className="h-40 w-full" />}
        {q.error && (
          <Alert variant="destructive">
            <AlertDescription>{String(q.error)}</AlertDescription>
          </Alert>
        )}
        {q.data && (
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Horário</TableHead>
                <TableHead>Tipo</TableHead>
                <TableHead>Ator</TableHead>
                <TableHead>Recurso</TableHead>
                <TableHead>Resultado</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {q.data.items.map((e) => (
                <TableRow key={e.id}>
                  <TableCell className="whitespace-nowrap text-xs">
                    {e.timestamp}
                  </TableCell>
                  <TableCell>{e.type}</TableCell>
                  <TableCell>{e.actor ?? "—"}</TableCell>
                  <TableCell>{e.resource ?? "—"}</TableCell>
                  <TableCell>{e.outcome ?? "—"}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        )}
      </CardContent>
    </Card>
  );
}