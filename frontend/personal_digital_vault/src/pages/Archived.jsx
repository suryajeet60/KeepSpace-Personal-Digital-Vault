import { useEffect, useState } from "react";
import axios from "axios";
import VaultCard from "../components/VaultCard";

function Archived() {
  const [items, setItems] = useState([]);

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/vault", {
        withCredentials: true,
      })
      .then((res) => {
        const archived = res.data.filter(
          (item) => item.archived
        );

        setItems(archived);
      })
      .catch((error) => {
        console.error(
          "Failed to load archived items:",
          error
        );
      });
  }, []);

  return (
    <div>
      <h2>📦 Archived Items</h2>

      {items.length === 0 ? (
        <div className="empty-state">
          <h3>No Archived Items</h3>
          <p>
            Items you archive will appear here.
          </p>
        </div>
      ) : (
        <div className="vault-grid">
          {items.map((item) => (
            <VaultCard
              key={item.id}
              item={item}
            />
          ))}
        </div>
      )}
    </div>
  );
}

export default Archived;