import { useQuery } from "@tanstack/react-query";
import { client } from "../lib/client";

export default function Audit() {
  const { data } = useQuery({
    queryKey: ["audit"],
    queryFn: () => client.listAudit({
      from: new Date(Date.now() - 86400000).toISOString(),
      to:   new Date().toISOString(),
      size: 50,
    }),
  });
  return (
    <div>
      <h2>Audit (últimas 24h)</h2>
      <pre>{JSON.stringify(data, null, 2)}</pre>
    </div>
  );
}
