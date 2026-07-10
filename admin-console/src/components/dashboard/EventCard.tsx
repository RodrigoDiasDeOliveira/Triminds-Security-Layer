interface Props {
  title: string;
  time: string;
}

export default function EventCard({ title, time }: Props) {
  return (
    <div className="border-b border-slate-700 py-3">
      <p className="font-medium text-white">
        {title}
      </p>

      <p className="text-sm text-slate-500">
        {time}
      </p>
    </div>
  );
}