import { useEffect, useState } from "react";
import axios from "axios";

import HeroSection from "../components/HeroSection";
import StatCard from "../components/StatCard";
import Loader from "../components/Loader";

function Dashboard() {
  const [analytics, setAnalytics] = useState(null);
  const [recentItems, setRecentItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const [userName, setUserName] = useState("User");

  useEffect(() => {
    const storedName =
      localStorage.getItem("fullName");

    if (storedName) {
      setUserName(storedName);
    }

    fetchDashboardData();
  }, []);

  const fetchDashboardData = async () => {
    try {
      const analyticsResponse = await axios.get(
        "http://localhost:8080/api/vault/analytics",
        {
          withCredentials: true,
        }
      );

      const recentResponse = await axios.get(
        "http://localhost:8080/api/vault/recent",
        {
          withCredentials: true,
        }
      );

      setAnalytics(analyticsResponse.data);
      setRecentItems(recentResponse.data);
    } catch (error) {
      console.error("Dashboard Error:", error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <Loader text="Loading Dashboard..." />
    );
  }

  return (
    <div>
      {/* Hero Section */}
      <HeroSection
        userName={userName}
        totalItems={analytics?.totalItems || 0}
        favorites={analytics?.favoriteItems || 0}
        highPriority={
          recentItems.filter(
            (item) => item.priority === "High"
          ).length
        }
      />

      <h1>Dashboard</h1>

      <hr />

      <h2>Analytics</h2>

      <div className="stats-container">
        <StatCard
          title="Total Items"
          value={analytics?.totalItems || 0}
        />

        <StatCard
          title="Categories"
          value={analytics?.totalCategories || 0}
        />

        <StatCard
          title="Favorites"
          value={analytics?.favoriteItems || 0}
        />

        <StatCard
          title="Archived"
          value={analytics?.archivedItems || 0}
        />

        <StatCard
          title="Latest Saved Item"
          value={
            analytics?.latestSavedItem || "N/A"
          }
        />
      </div>

      <hr />

      <h2>Recent Items</h2>

      {recentItems.length === 0 ? (
        <p>No Items Found</p>
      ) : (
        recentItems.map((item) => (
          <div
            key={item.id}
            style={{
              border: "1px solid gray",
              padding: "10px",
              marginBottom: "10px",
              borderRadius: "10px",
              background: "#fff",
            }}
          >
            <h3>{item.title}</h3>

            <p>
              Category:{" "}
              {item.category || "N/A"}
            </p>

            <p>
              Priority:{" "}
              {item.priority || "N/A"}
            </p>

            <p>
              Created{" "}
              {new Date(
                item.createdAt
              ).toLocaleString()}
            </p>
          </div>
        ))
      )}
    </div>
  );
}

export default Dashboard;