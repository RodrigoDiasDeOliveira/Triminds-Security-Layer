import { createFileRoute } from "@tanstack/react-router";
import { useQuery } from "@tanstack/react-query";
import { intel as intelApi } from "@/lib/triminds-client";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Alert, AlertDescription } from "@/components/ui/alert";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
export const Route = createFileRoute("/_authenticated/intel")({
  head: () => ({ meta: [{ title: "Intelligence — Triminds" }] }),
  component: IntelPage,
});
function IntelPage() {
  const feeds = useQuery({ queryKey: ["admin", "intel", "feeds"], queryFn: () => intelApi.feeds() });
  const iocs = useQuery({ queryKey: ["admin", "intel", "iocs"], queryFn: () => intelApi.iocs({ page: 0, size: 50 }) });
  return (
    <div className="space-y-6">
      <Card>
        <CardHeader><CardTitle>Feeds</CardTitle><CardDescription>Fontes conectadas.</CardDescription></CardHeader>
        <CardContent>
          {feeds.error && <Alert variant="destructive"><AlertDescription>{String(feeds.error)}</AlertDescription></Alert>}
          {feeds.data && (
            <Table>
              <TableHeader><TableRow><TableHead>Nome</TableHead><TableHead>Provedor</TableHead><TableHead>Última sync</TableHead><TableHead>Itens</TableHead></TableRow></TableHeader>
              <TableBody>
                {feeds.data.map((f) => (
                  <TableRow key={f.id}>
                    <TableCell className="font-medium">{f.name}</TableCell>
                    <TableCell>{f.provider ?? "—"}</TableCell>
                    <TableCell className="whitespace-nowrap text-xs">{f.lastSyncAt ?? "—"}</TableCell>
                    <TableCell>{f.itemCount ?? "—"}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          )}
        </CardContent>
      </Card>
      <Card>
        <CardHeader><CardTitle>IOCs</CardTitle></CardHeader>
        <CardContent>
          {iocs.error && <Alert variant="destructive"><AlertDescription>{String(iocs.error)}</AlertDescription></Alert>}
          {iocs.data && (
            <Table>
              <TableHeader><TableRow><TableHead>Tipo</TableHead><TableHead>Valor</TableHead><TableHead>Fonte</TableHead><TableHead>Confiança</TableHead></TableRow></TableHeader>
              <TableBody>
                {iocs.data.items.map((i) => (
                  <TableRow key={i.id}>
                    <TableCell>{i.type}</TableCell>
                    <TableCell className="font-mono text-xs">{i.value}</TableCell>
                    <TableCell>{i.source ?? "—"}</TableCell>
                    <TableCell>{i.confidence ?? "—"}</TableCell>
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