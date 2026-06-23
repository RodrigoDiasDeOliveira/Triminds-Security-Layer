import { useState } from "react";
import { client } from "../lib/client";

export default function Policies() {
  const [out, setOut] = useState<unknown>(null);
  const [input, setInput] = useState('{"action":"read","resource":"doc:42"}');
  const run = async () => setOut(await client.evaluatePolicy(JSON.parse(input)));
  return (
    <div>
      <h2>Policies</h2>
      <textarea value={input} onChange={e => setInput(e.target.value)} rows={8} cols={60} />
      <br /><button onClick={run}>Evaluate</button>
      <pre>{JSON.stringify(out, null, 2)}</pre>
    </div>
  );
}
