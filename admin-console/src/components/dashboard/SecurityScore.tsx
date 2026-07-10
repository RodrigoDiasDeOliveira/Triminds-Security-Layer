interface Props {
  score: number;
}

export default function SecurityScore({ score }: Props) {
  const color =
    score >= 80
      ? "text-green-400"
      : score >= 60
      ? "text-yellow-400"
      : "text-red-400";

  return (
    <div className="rounded-xl border border-slate-700 bg-slate-800 p-6">
      <p className="text-sm text-slate-400">
        Security Score
      </p>

      <h1 className={`mt-2 text-5xl font-bold ${color}`}>
        {score}
      </h1>

      <div className="mt-4 h-3 overflow-hidden rounded-full bg-slate-700">
        <div
          className="h-full rounded-full bg-green-500"
          style={{ width: `${score}%` }}
        />
      </div>
    </div>
  );
}