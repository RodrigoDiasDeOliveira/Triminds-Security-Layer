import { ButtonHTMLAttributes } from "react";

interface Props extends ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: "primary" | "secondary" | "danger" | "success";
}

export default function Button({
  children,
  variant = "primary",
  className = "",
  ...props
}: Props) {
  const colors = {
    primary:
      "bg-blue-600 hover:bg-blue-700 text-white",

    secondary:
      "bg-slate-700 hover:bg-slate-600 text-white",

    danger:
      "bg-red-600 hover:bg-red-700 text-white",

    success:
      "bg-green-600 hover:bg-green-700 text-white",
  };

  return (
    <button
      {...props}
      className={`rounded-lg px-4 py-2 font-medium transition ${colors[variant]} ${className}`}
    >
      {children}
    </button>
  );
}