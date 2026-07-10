import { createFileRoute, useNavigate } from "@tanstack/react-router";
import { useState } from "react";
import { policies as policiesApi } from "@/lib/triminds-client";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Textarea } from "@/components/ui/textarea";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Alert, AlertDescription } from "@/components/ui/alert";
import { toast } from "sonner";
export const Route = createFileRoute("/_authenticated/policies/new")({
  head: () => ({ meta: [{ title: "Nova política — Triminds" }] }),
  component: NewPolicyPage,
});
const DEFAULT_REGO = `package triminds.example
default allow = false
allow {
  input.action == "read"
  input.subject.role == "admin"
}`;
function NewPolicyPage() {
  const navigate = useNavigate();
  const [id, setId] = useState("");
  const [name, setName] = useState("");
  const [rego, setRego] = useState(DEFAULT_REGO);
  const [testInput, setTestInput] = useState(
    '{"action":"read","subject":{"role":"admin"}}',
  );
  const [testResult, setTestResult] = useState<unknown>(null);
  const [error, setError] = useState<string | null>(null);
  const [saving, setSaving] = useState(false);
  const [testing, setTesting] = useState(false);
  const save = async () => {
    setError(null);
    setSaving(true);
    try {
      await policiesApi.upsert({ id, name, rego });
      toast.success("Política salva");
      navigate({ to: "/policies" });
    } catch (e) {
      setError(e instanceof Error ? e.message : String(e));
    } finally {
      setSaving(false);
    }
  };
  const test = async () => {
    setError(null);
    setTesting(true);
    try {
      const parsed = JSON.parse(testInput) as Record<string, unknown>;
      setTestResult(await policiesApi.evaluate(parsed));
    } catch (e) {
      setError(e instanceof Error ? e.message : String(e));
    } finally {
      setTesting(false);
    }
  };
  return (
    <Card className="mx-auto max-w-4xl">
      <CardHeader>
        <CardTitle>Nova política</CardTitle>
        <CardDescription>
          Escreva o documento em Rego e valide antes de publicar.
        </CardDescription>
      </CardHeader>
      <CardContent className="space-y-4">
        <div className="grid gap-4 sm:grid-cols-2">
          <div className="space-y-2">
            <Label htmlFor="id">ID</Label>
            <Input id="id" value={id} onChange={(e) => setId(e.target.value)} />
          </div>
          <div className="space-y-2">
            <Label htmlFor="name">Nome</Label>
            <Input id="name" value={name} onChange={(e) => setName(e.target.value)} />
          </div>
        </div>
        <div className="space-y-2">
          <Label>Rego</Label>
          <Textarea
            className="font-mono text-sm"
            rows={12}
            value={rego}
            onChange={(e) => setRego(e.target.value)}
          />
        </div>
        <div className="space-y-2">
          <Label>Input de teste (JSON)</Label>
          <Textarea
            className="font-mono text-sm"
            rows={5}
            value={testInput}
            onChange={(e) => setTestInput(e.target.value)}
          />
        </div>
        {error && (
          <Alert variant="destructive">
            <AlertDescription>{error}</AlertDescription>
          </Alert>
        )}
        {testResult !== null && (
          <pre className="overflow-auto rounded-md bg-muted p-3 text-xs">
            {JSON.stringify(testResult, null, 2)}
          </pre>
        )}
        <div className="flex gap-2">
          <Button onClick={test} variant="outline" disabled={testing}>
            {testing ? "Avaliando..." : "Testar"}
          </Button>
          <Button onClick={save} disabled={!id || !name || saving}>
            {saving ? "Salvando..." : "Salvar"}
          </Button>
        </div>
      </CardContent>
    </Card>
  );
}