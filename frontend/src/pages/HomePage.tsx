import type { HomeResponse } from "../types/review";
import { useState } from "react";
import { useEffect } from "react";
import { getHomeData } from "../api/homepage";
import { ReviewCard } from "../Components/ReviewCard";

export default function HomePage() {
  const [data, setData] = useState<HomeResponse | null>(null);

  useEffect(() => {
    getHomeData().then(setData);
  }, []);

  if (!data) return <div>Loading...</div>;

  return (
    <div className="home-container">
      <section className="hero">
        <h1>{data.landingPageMessage}</h1>
        <p>Total reviews: {data.totalReviews}</p>
      </section>

      <section className="recent-reviews">
        <h2>Recent reviews:</h2>
        <div className="review-grid">
          {data.recentReviews.map(r => (
            <ReviewCard key={r.id} review={r} />
          ))}
        </div>
      </section>
    </div>
  );
}