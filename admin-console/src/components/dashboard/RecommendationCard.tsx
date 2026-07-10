interface Props {
  message: string;
}

export default function RecommendationCard({ message }: Props) {
  return (
    <div className="rounded-lg border border-blue-800 bg-blue-950 p-4">
      <p className="text-blue-200">
        💡 {message}
      </p>
    </div>
  );
}