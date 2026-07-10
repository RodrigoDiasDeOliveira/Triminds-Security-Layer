import { TrimindsClient, CheckPermissionInput } from "./client";
export declare const useCheckPermission: (c: TrimindsClient, input: CheckPermissionInput) => import("@tanstack/react-query").UseQueryResult<boolean, Error>;
export declare const useAssignRole: (c: TrimindsClient) => import("@tanstack/react-query").UseMutationResult<void, Error, {
    identityId: string;
    roleId: string;
}, unknown>;
export declare const useRevokeRole: (c: TrimindsClient) => import("@tanstack/react-query").UseMutationResult<void, Error, {
    identityId: string;
    roleId: string;
}, unknown>;
