import { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import axios from "axios";

function ViewItem() {
  const { id } = useParams();

  const [item, setItem] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    fetchItem();
  }, []);

  const fetchItem = async () => {
    try {
      const token =
        localStorage.getItem("token");

      const response =
        await axios.get(
          `http://localhost:8080/api/vault/${id}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );

      setItem(response.data);
    } catch (err) {
      console.error(err);

      setError(
        "Failed to load item."
      );
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <div className="page-container">
        <h2>Loading...</h2>
      </div>
    );
  }

  if (error) {
    return (
      <div className="page-container">
        <h2>{error}</h2>
      </div>
    );
  }

  return (
    <div className="page-container">

      <div className="view-card">

        <h1>{item.title}</h1>

        <hr />

        <p>
          <strong>Category:</strong>{" "}
          {item.category}
        </p>

        <p>
          <strong>Tags:</strong>{" "}
          {item.tags || "N/A"}
        </p>

        <p>
          <strong>Priority:</strong>{" "}
          {item.priority || "N/A"}
        </p>

        <p>
          <strong>Favorite:</strong>{" "}
          {item.favorite
            ? "Yes"
            : "No"}
        </p>

        <p>
          <strong>Archived:</strong>{" "}
          {item.archived
            ? "Yes"
            : "No"}
        </p>

        <p>
          <strong>Reference URL:</strong>{" "}
          {item.referenceUrl ||
            "N/A"}
        </p>

        <p>
          <strong>Created:</strong>{" "}
          {new Date(
            item.createdAt
          ).toLocaleString()}
        </p>

        <hr />

        <h3>Content</h3>

        <div className="view-content">
          {item.content}
        </div>

        <div className="view-actions">

          <Link
            to={`/edit/${item.id}`}
          >
            <button className="btn btn-primary">
              Edit
            </button>
          </Link>

          <Link to="/vault">
            <button className="btn btn-secondary">
              Back
            </button>
          </Link>

        </div>

      </div>

    </div>
  );
}

export default ViewItem;