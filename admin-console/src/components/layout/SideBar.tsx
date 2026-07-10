import {
  LayoutDashboard,
  Users,
  Shield,
  ScrollText,
  ClipboardList,
  AlertTriangle,
  Settings,
} from "lucide-react";
import { NavLink } from "react-router-dom";

const menu = [
  { to: "/", label: "Dashboard", icon: LayoutDashboard },
  { to: "/roles", label: "Roles", icon: Shield },
  { to: "/policies", label: "Policies", icon: ScrollText },
  { to: "/audit", label: "Audit", icon: ClipboardList },
  { to: "/risk", label: "Risk Engine", icon: AlertTriangle },
  { to: "/identity", label: "Identity", icon: Users },
];

export default function Sidebar() {
  return (
    <aside className="flex h-screen w-64 flex-col border-r border-slate-800 bg-slate-950">
      <div className="border-b border-slate-800 p-6">
        <h1 className="text-xl font-bold text-white">
          🛡 Triminds
        </h1>

        <p className="mt-1 text-xs text-slate-400">
          Security Layer
        </p>
      </div>

      <nav className="flex-1 p-4 space-y-2">
        {menu.map(({ to, label, icon: Icon }) => (
          <NavLink
            key={to}
            to={to}
            className={({ isActive }) =>
              `flex items-center gap-3 rounded-lg px-3 py-2 transition ${
                isActive
                  ? "bg-blue-600 text-white"
                  : "text-slate-300 hover:bg-slate-800"
              }`
            }
          >
            <Icon size={18} />
            {label}
          </NavLink>
        ))}
      </nav>

      <div className="border-t border-slate-800 p-4 text-xs text-slate-500">
        v0.1.0
      </div>
    </aside>
  );
}