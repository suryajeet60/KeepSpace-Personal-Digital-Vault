import { useEffect, useState } from "react";
import axios from "axios";
import VaultCard from "../components/VaultCard";

function Favorites() {
  const [items, setItems] = useState([]);

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/vault", {
        withCredentials: true,
      })
      .then((res) => {
        const favorites = res.data.filter(
          (item) => item.favorite
        );

        setItems(favorites);
      })
      .catch((error) => {
        console.error(
          "Failed to load favorite items:",
          error
        );
      });
  }, []);

  return (
    <div>
      <h2>⭐ Favorite Items</h2>

      {items.length === 0 ? (
        <div className="empty-state">
          <h3>No Favorite Items</h3>
          <p>
            Mark items as favorites to see them here.
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

export default Favorites;