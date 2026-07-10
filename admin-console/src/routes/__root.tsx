routes/__root.tsx
import { Button } from "@/components/ui/button";
import { useTrimindsAuth } from "@/hooks/use-triminds-auth";
import { clearToken } from "@/lib/triminds-auth";
import { Toaster } from "@/components/ui/sonner";
function NotFoundComponent() {
  return (
          {/* Required: nested routes render here. Removing <Outlet /> breaks all child routes. */}
          <Outlet />
        </main>
        <Toaster richColors position="top-right" />
      </div>
    </QueryClientProvider>
  );
      <div className="mx-auto flex max-w-6xl flex-wrap items-center gap-4 px-6 py-3 text-sm">
        <span className="font-semibold">Triminds</span>
        <Link to="/" className="hover:underline" activeProps={{ className: "font-medium underline" }}>Dashboard</Link>
        <Link to="/identities" className="hover:underline" activeProps={{ className: "font-medium underline" }}>Identities</Link>
        <Link to="/roles" className="hover:underline" activeProps={{ className: "font-medium underline" }}>Roles</Link>
        <Link to="/policies" className="hover:underline" activeProps={{ className: "font-medium underline" }}>Policies</Link>
        <Link to="/risk" className="hover:underline" activeProps={{ className: "font-medium underline" }}>Risk</Link>
        <Link to="/intel" className="hover:underline" activeProps={{ className: "font-medium underline" }}>Intel</Link>
        <Link to="/audit" className="hover:underline" activeProps={{ className: "font-medium underline" }}>Audit</Link>
        <Link to="/settings" className="hover:underline" activeProps={{ className: "font-medium underline" }}>Settings</Link>
        <div className="ml-auto flex items-center gap-2">
          {isAuthenticated ? (
            <Button={() => { clearToken(); logout(); }} variant="destructive" size="sm">Logout</Button>
          ) : (
            <Button onClick={login} variant="default" size="sm">Login</Button>
          )}
        </div>
      </div>
    </header>
  );
}               