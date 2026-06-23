import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { TrimindsClient, CheckPermissionInput } from "./client";

export const useCheckPermission = (c: TrimindsClient, input: CheckPermissionInput) =>
  useQuery({
    queryKey: ["triminds", "check", input],
    queryFn: () => c.checkPermission(input),
  });

export const useAssignRole = (c: TrimindsClient) => {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: (v: { identityId: string; roleId: string }) =>
      c.assignRole(v.identityId, v.roleId),
    onSuccess: () => qc.invalidateQueries({ queryKey: ["triminds"] }),
  });
};

export const useRevokeRole = (c: TrimindsClient) => {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: (v: { identityId: string; roleId: string }) =>
      c.revokeRole(v.identityId, v.roleId),
    onSuccess: () => qc.invalidateQueries({ queryKey: ["triminds"] }),
  });
};
