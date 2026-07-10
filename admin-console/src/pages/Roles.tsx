import { useState } from "react";
import { useAssignRole, useRevokeRole } from "@triminds/sdks";
import { client } from "../lib/client";

export default function Roles() {
  const [identityId, setIdentity] = useState("");
  const [roleId, setRole] = useState("");
  const assign = useAssignRole(client);
  const revoke = useRevokeRole(client);

  return (
    <div>
      <h2>Roles</h2>
      <input placeholder="identityId" value={identityId} onChange={e => setIdentity(e.target.value)} />
      <input placeholder="roleId"     value={roleId}     onChange={e => setRole(e.target.value)} />
      <button onClick={() => assign.mutate({ identityId, roleId })}>Assign</button>
      <button onClick={() => revoke.mutate({ identityId, roleId })}>Revoke</button>
    </div>
  );
}
