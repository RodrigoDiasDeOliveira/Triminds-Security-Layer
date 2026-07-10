interface Props {
  children: React.ReactNode;
  color?: "blue" | "green" | "red" | "yellow" | "gray";
}

export default function Badge({
  children,
  color = "blue",
}: Props) {
  const colors = {
    blue: "bg-blue-600",
    green: "bg-green-600",
    red: "bg-red-600",
    yellow: "bg-yellow-500 text-black",
    gray: "bg-slate-600",
  };

  return (
    <span
      className={`rounded-full px-3 py-1 text-xs font-semibold text-white ${colors[color]}`}
    >
      {children}
    </span>
  );
}