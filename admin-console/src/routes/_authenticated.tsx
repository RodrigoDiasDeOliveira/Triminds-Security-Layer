outes/_authenticated.tsx
import { createFileRoute, Outlet, redirect } from "@tanstack/react-router";
import { getToken } from "@/lib/triminds-auth";
export const Route = createFileRoute("/_authenticated")({
  beforeLoad: ({ location }) => {
    if (typeof window === "undefined") return;
    if (!getToken()) {
      throw redirect({
        to: "/login",
        search: { redirect: location.href },
      });
    }
  },
  component: () => <Outlet />,
});