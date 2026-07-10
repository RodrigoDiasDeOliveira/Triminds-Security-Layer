interface Props {
  title: string;
  value: string | number;
}

export default function StatCard({ title, value }: Props) {
  return (
    <div className="rounded-xl border border-slate-800 bg-slate-800 p-6 shadow">
      <p className="text-sm text-slate-400">
        {title}
      </p>

      <h2 className="mt-2 text-3xl font-bold text-white">
        {value}
      </h2>
    </div>
  );
}