import { createFileRoute, Link } from "@tanstack/react-router";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { useState } from "react";
import { identities as api } from "@/lib/triminds-client";
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
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { Badge } from "@/components/ui/badge";
import { toast } from "sonner";
export const Route = createFileRoute("/_authenticated/identities")({
  head: () => ({ meta: [{ title: "Identities — Triminds" }] }),
  component: IdentitiesPage,
});
function IdentitiesPage() {
  const [query, setQuery] = useState("");
  const qc = useQueryClient();
  const list = useQuery({
    queryKey: ["admin", "identities", query],
    queryFn: () => api.list({ query, page: 0, size: 50 }),
  });
  const [open, setOpen] = useState(false);
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const create = useMutation({
    mutationFn: () => api.create({ username, email, password }),
    onSuccess: () => {
      toast.success("Identidade criada");
      setOpen(false);
      setUsername("");
      setEmail("");
      setPassword("");
      qc.invalidateQueries({ queryKey: ["admin", "identities"] });
    },
    onError: (e) => toast.error(String(e)),
  });
  return (
    <Card>
      <CardHeader className="flex flex-row items-center justify-between gap-4">
        <div>
          <CardTitle>Identidades</CardTitle>
          <CardDescription>Usuários e serviços gerenciados.</CardDescription>
        </div>
        <div className="flex items-center gap-2">
          <Input
            className="w-56"
            placeholder="Buscar…"
            value={query}
            onChange={(e) => setQuery(e.target.value)}
          />
          <Dialog open={open} onOpenChange={setOpen}>
            <DialogTrigger asChild>
              <Button>Nova identidade</Button>
            </DialogTrigger>
            <DialogContent>
              <DialogHeader>
                <DialogTitle>Nova identidade</DialogTitle>
                <DialogDescription>
                  Cria uma identidade no <code>security-identity</code>.
                </DialogDescription>
              </DialogHeader>
              <div className="space-y-3">
                <div className="space-y-2">
                  <Label htmlFor="new-username">Usuário</Label>
                  <Input
                    id="new-username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                  />
                </div>
                <div className="space-y-2">
                  <Label htmlFor="new-email">Email</Label>
                  <Input
                    id="new-email"
                    type="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                  />
                </div>
                <div className="space-y-2">
                  <Label htmlFor="new-password">Senha inicial</Label>
                  <Input
                    id="new-password"
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                  />
                </div>
              </div>
              <DialogFooter>
                <Button
                  onClick={() => create.mutate()}
                  disabled={!username || create.isPending}
                >
                  {create.isPending ? "Criando…" : "Criar"}
                </Button>
              </DialogFooter>
            </DialogContent>
          </Dialog>
        </div>
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
                <TableHead>Usuário</TableHead>
                <TableHead>Email</TableHead>
                <TableHead>Status</TableHead>
                <TableHead></TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {list.data.items.map((i) => (
                <TableRow key={i.id}>
                  <TableCell className="font-medium">{i.username}</TableCell>
                  <TableCell>{i.email ?? "—"}</TableCell>
                  <TableCell>
                    <Badge variant={i.status === "active" ? "default" : "secondary"}>
                      {i.status ?? "—"}
                    </Badge>
                  </TableCell>
                  <TableCell className="text-right">
                    <Button variant="ghost" size="sm" asChild>
                      <Link to="/identities/$id" params={{ id: i.id }}>
                        Abrir
                      </Link>
                    </Button>
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