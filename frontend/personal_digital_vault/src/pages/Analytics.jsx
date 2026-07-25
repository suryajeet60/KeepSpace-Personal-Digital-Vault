import { useEffect, useState } from "react";
import axios from "axios";

import EmptyState from "../components/EmptyState";

function Analytics() {
  const [analytics, setAnalytics] =
    useState(null);

  const [categoryStats, setCategoryStats] =
    useState([]);

  const [loading, setLoading] =
    useState(true);

  useEffect(() => {
    fetchAnalytics();
  }, []);

  const fetchAnalytics = async () => {
    try {
      const analyticsResponse =
        await axios.get(
          "http://localhost:8080/api/vault/analytics",
          {
            withCredentials: true,
          }
        );

      const categoryResponse =
        await axios.get(
          "http://localhost:8080/api/vault/category-stats",
          {
            withCredentials: true,
          }
        );

      setAnalytics(
        analyticsResponse.data
      );

      setCategoryStats(
        categoryResponse.data
      );
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <h2>Loading Analytics...</h2>;
  }

  return (
  <div className="analytics-page">
    <div className="analytics-header">
      <h1>📊 Analytics Dashboard</h1>
      <p>Insights about your personal vault</p>
    </div>

    <div className="analytics-grid">
      {[
        ["📁", "Total Items", analytics?.totalItems],
        ["🗂️", "Categories", analytics?.totalCategories],
        ["⭐", "Favorites", analytics?.favoriteItems],
        ["📦", "Archived", analytics?.archivedItems],
        ["🏆", "Most Used", analytics?.mostUsedCategory],
        ["📝", "Latest Saved", analytics?.latestSavedItem],
      ].map(([icon, title, value]) => (
        <div className="analytics-card" key={title}>
          <span>{icon}</span>
          <h3>{title}</h3>
          <h2>{value}</h2>
        </div>
      ))}
    </div>

    <div className="category-section">
      <h2>Category Statistics</h2>

      {categoryStats.length ? (
        <div className="category-grid">
          {categoryStats.map((c) => (
            <div className="category-card" key={c.category}>
              <div className="category-title">{c.category}</div>
              <div className="category-count">{c.count}</div>
              <p>Items Stored</p>

              <div className="progress">
                <div
                  className="progress-fill"
                  style={{
                    width: `${(c.count / analytics.totalItems) * 100}%`,
                  }}
                />
              </div>
            </div>
          ))}
        </div>
      ) : (
        <EmptyState
          title="No Category Data Found"
          description="Category statistics will appear here."
        />
      )}
    </div>
  </div>
);
}

export default Analytics;