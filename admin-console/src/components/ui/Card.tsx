import { ReactNode } from "react";

interface CardProps {
  title?: string;
  subtitle?: string;
  children: ReactNode;
  className?: string;
}

export default function Card({
  title,
  subtitle,
  children,
  className = "",
}: CardProps) {
  return (
    <div
      className={`rounded-xl border border-slate-800 bg-slate-800 shadow-lg ${className}`}
    >
      {(title || subtitle) && (
        <div className="border-b border-slate-700 px-6 py-4">
          {title && (
            <h2 className="text-lg font-semibold text-white">
              {title}
            </h2>
          )}

          {subtitle && (
            <p className="mt-1 text-sm text-slate-400">
              {subtitle}
            </p>
          )}
        </div>
      )}

      <div className="p-6">{children}</div>
    </div>
  );
}