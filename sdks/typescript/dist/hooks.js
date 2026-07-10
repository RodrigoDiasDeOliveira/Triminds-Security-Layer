import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
export const useCheckPermission = (c, input) => useQuery({
    queryKey: ["triminds", "check", input],
    queryFn: () => c.checkPermission(input),
});
export const useAssignRole = (c) => {
    const qc = useQueryClient();
    return useMutation({
        mutationFn: (v) => c.assignRole(v.identityId, v.roleId),
        onSuccess: () => qc.invalidateQueries({ queryKey: ["triminds"] }),
    });
};
export const useRevokeRole = (c) => {
    const qc = useQueryClient();
    return useMutation({
        mutationFn: (v) => c.revokeRole(v.identityId, v.roleId),
        onSuccess: () => qc.invalidateQueries({ queryKey: ["triminds"] }),
    });
};
