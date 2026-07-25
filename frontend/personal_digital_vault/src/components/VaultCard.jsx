import { Link } from "react-router-dom";
import CategoryBadge from "./CategoryBadge";

function VaultCard({
  item,
  onDelete,
}) {
  return (
    <div className="vault-card">

      <div className="vault-header">
        <h2>{item.title}</h2>

        <CategoryBadge
          category={item.category}
        />
      </div>

      <div className="vault-content">

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
          <strong>Created:</strong>{" "}
          {new Date(
            item.createdAt
          ).toLocaleString()}
        </p>

      </div>

      <div className="vault-actions">

        <Link
          to={`/item/${item.id}`}
        >
          <button className="btn btn-success">
            View
          </button>
        </Link>

        <Link
          to={`/edit/${item.id}`}
        >
          <button className="btn btn-primary">
            Edit
          </button>
        </Link>

        <button
          className="btn btn-danger"
          onClick={() =>
            onDelete(item.id)
          }
        >
          Delete
        </button>

      </div>

    </div>
  );
}

export default VaultCard;