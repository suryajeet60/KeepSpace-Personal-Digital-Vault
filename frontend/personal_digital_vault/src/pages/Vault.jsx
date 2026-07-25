import { useEffect, useState } from "react";
import axios from "axios";

import VaultCard from "../components/VaultCard";
import EmptyState from "../components/EmptyState";

function Vault() {
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchItems();
  }, []);

  const fetchItems = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8080/api/vault",
        {
          withCredentials: true,
        }
      );

      setItems(response.data);
    } catch (error) {
      console.error("Failed to fetch items:", error);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    const confirmDelete = window.confirm(
      "Delete this item?"
    );

    if (!confirmDelete) return;

    try {
      await axios.delete(
        `http://localhost:8080/api/vault/${id}`,
        {
          withCredentials: true,
        }
      );

      setItems((prevItems) =>
        prevItems.filter(
          (item) => item.id !== id
        )
      );
    } catch (error) {
      console.error(error);
      alert("Failed to delete item");
    }
  };

  if (loading) {
    return <h2>Loading Vault...</h2>;
  }

  return (
    <div>
      <h1>My Vault</h1>

      <hr />

      {items.length === 0 ? (
        <EmptyState
          title="No Items Found"
          description="Start adding items to your vault."
        />
      ) : (
        items.map((item) => (
          <VaultCard
            key={item.id}
            item={item}
            onDelete={handleDelete}
          />
        ))
      )}
    </div>
  );
}

export default Vault;