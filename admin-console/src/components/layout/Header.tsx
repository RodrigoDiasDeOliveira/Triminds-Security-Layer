import { Bell, UserCircle } from "lucide-react";

export default function Header() {
  return (
    <header className="flex h-16 items-center justify-between border-b border-slate-800 bg-slate-900 px-8">
      <div>
        <h2 className="text-xl font-semibold text-white">
          Triminds Security Layer
        </h2>
      </div>

      <div className="flex items-center gap-6">
        <Bell className="text-slate-400" />

        <div className="flex items-center gap-2">
          <UserCircle className="text-slate-300" />

          <div className="text-right">
            <p className="text-sm text-white">
              Rodrigo
            </p>

            <p className="text-xs text-slate-400">
              Default Tenant
            </p>
          </div>
        </div>
      </div>
    </header>
  );
}