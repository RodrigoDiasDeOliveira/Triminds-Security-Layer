interface Props {
  title: string;
  description: string;
  time: string;
}

export default function EventRow({
  title,
  description,
  time,
}: Props) {
  return (
    <div className="border-b border-slate-700 py-3">
      <h3 className="font-medium text-white">
        {title}
      </h3>

      <p className="text-sm text-slate-400">
        {description}
      </p>

      <p className="mt-1 text-xs text-slate-500">
        {time}
      </p>
    </div>
  );
}