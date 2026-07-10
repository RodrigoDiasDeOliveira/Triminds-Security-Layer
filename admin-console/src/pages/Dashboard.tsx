import MainLayout from "../components/layout/MainLayout";
import StatCard from "../components/ui/StatCard";

import StatusCard from "../components/dashboard/StatusCard";
import EventCard from "../components/dashboard/EventCard";
import RecommendationCard from "../components/dashboard/RecommendationCard";
import SecurityScore from "../components/dashboard/SecurityScore";

export default function Dashboard() {
  return (
    <MainLayout>
      <div className="space-y-8">

        <div>
          <h1 className="text-3xl font-bold text-white">
            Dashboard
          </h1>

          <p className="mt-2 text-slate-400">
            Welcome to Triminds Security Layer
          </p>
        </div>

        {/* KPIs */}

        <div className="grid gap-6 md:grid-cols-2 xl:grid-cols-4">
          <StatCard title="Identities" value="1,248" />
          <StatCard title="Roles" value="18" />
          <StatCard title="Policies" value="84" />
          <SecurityScore score={92} />
        </div>

        {/* Segunda linha */}

        <div className="grid gap-6 xl:grid-cols-2">

          <div className="rounded-xl border border-slate-800 bg-slate-800 p-6">

            <h2 className="mb-5 text-xl font-semibold text-white">
              Platform Health
            </h2>

            <div className="space-y-3">

              <StatusCard title="Gateway" status />

              <StatusCard title="Authentication Service" status />

              <StatusCard title="Identity Service" status />

              <StatusCard title="Policy Engine" status />

              <StatusCard title="Risk Engine" status />

              <StatusCard title="Audit Service" status />

            </div>

          </div>

          <div className="rounded-xl border border-slate-800 bg-slate-800 p-6">

            <h2 className="mb-5 text-xl font-semibold text-white">
              Recent Security Events
            </h2>

            <EventCard
              title="Administrator logged in"
              time="2 minutes ago"
            />

            <EventCard
              title="Policy ADMIN updated"
              time="5 minutes ago"
            />

            <EventCard
              title="Role assigned to user"
              time="12 minutes ago"
            />

            <EventCard
              title="Risk evaluation executed"
              time="20 minutes ago"
            />

          </div>

        </div>

        {/* IA */}

        <div className="rounded-xl border border-slate-800 bg-slate-800 p-6">

          <h2 className="mb-5 text-xl font-semibold text-white">
            AI Recommendations
          </h2>

          <div className="space-y-4">

            <RecommendationCard message="Enable MFA for administrator accounts." />

            <RecommendationCard message="Review the ADMIN policy permissions." />

            <RecommendationCard message="No anomalous tenant behavior detected." />

          </div>

        </div>

      </div>
    </MainLayout>
  );
}