import { ReactNode } from "react";

interface Props {
  headers: string[];
  children: ReactNode;
}

export default function Table({
  headers,
  children,
}: Props) {
  return (
    <div className="overflow-hidden rounded-xl border border-slate-800">
      <table className="w-full border-collapse">
        <thead className="bg-slate-900">
          <tr>
            {headers.map((h) => (
              <th
                key={h}
                className="px-4 py-3 text-left text-sm font-semibold text-slate-300"
              >
                {h}
              </th>
            ))}
          </tr>
        </thead>

        <tbody className="divide-y divide-slate-800 bg-slate-800">
          {children}
        </tbody>
      </table>
    </div>
  );
}