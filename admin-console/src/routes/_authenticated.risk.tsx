import { createFileRoute } from "@tanstack/react-router";
import { useMutation, useQuery } from "@tanstack/react-query";
import { useState } from "react";
import { risk as riskApi } from "@/lib/triminds-client";
import { Button } from "@/components/ui/button";
import { Textarea } from "@/components/ui/textarea";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Alert, AlertDescription } from "@/components/ui/alert";
import { Badge } from "@/components/ui/badge";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
export const Route = createFileRoute("/_authenticated/risk")({
  head: () => ({ meta: [{ title: "Risk — Triminds" }] }),
  component: RiskPage,
});
function RiskPage() {
  const signals = useQuery({
    queryKey: ["admin", "risk", "signals"],
    queryFn: () => riskApi.signals({ page: 0, size: 50 }),
  });
  const [input, setInput] = useState('{"subject":"user:42","action":"login","ip":"1.2.3.4"}');
  const score = useMutation({
    mutationFn: () => riskApi.score(JSON.parse(input) as Record<string, unknown>),
  });
  return (
    <div className="space-y-6">
      <Card>
        <CardHeader>
          <CardTitle>Score ad-hoc</CardTitle>
          <CardDescription>Envia evento para o <code>security-risk-engine</code>.</CardDescription>
        </CardHeader>
        <CardContent className="space-y-3">
          <Textarea className="font-mono text-sm" rows={5} value={input} onChange={(e) => setInput(e.target.value)} />
          <Button onClick={() => score.mutate()} disabled={score.isPending}>
            {score.isPending ? "Calculando…" : "Score"}
          </Button>
          {score.error && <Alert variant="destructive"><AlertDescription>{String(score.error)}</AlertDescription></Alert>}
          {score.data && <pre className="overflow-auto rounded-md bg-muted p-3 text-xs">{JSON.stringify(score.data, null, 2)}</pre>}
        </CardContent>
      </Card>
      <Card>
        <CardHeader><CardTitle>Sinais recentes</CardTitle></CardHeader>
        <CardContent>
          {signals.isLoading && <p className="text-sm text-muted-foreground">Carregando…</p>}
          {signals.error && <Alert variant="destructive"><AlertDescription>{String(signals.error)}</AlertDescription></Alert>}
          {signals.data && (
            <Table>
              <TableHeader><TableRow>
                <TableHead>Sujeito</TableHead><TableHead>Score</TableHead><TableHead>Severidade</TableHead><TableHead>Motivo</TableHead><TableHead>Observado</TableHead>
              </TableRow></TableHeader>
              <TableBody>
                {signals.data.items.map((s) => (
                  <TableRow key={s.id}>
                    <TableCell className="font-mono text-xs">{s.subject}</TableCell>
                    <TableCell>{s.score}</TableCell>
                    <TableCell><Badge variant="secondary">{s.severity}</Badge></TableCell>
                    <TableCell className="text-muted-foreground">{s.reason ?? "—"}</TableCell>
                    <TableCell className="whitespace-nowrap text-xs">{s.observedAt}</TableCell>
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