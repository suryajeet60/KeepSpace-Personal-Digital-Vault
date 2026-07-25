import { useState } from "react";
import axios from "axios";

import SearchBar from "../components/SearchBar";
import EmptyState from "../components/EmptyState";

function Search() {
  const [keyword, setKeyword] = useState("");
  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(false);

  const handleSearch = async (value) => {
    setKeyword(value);

    if (!value.trim()) {
      setResults([]);
      return;
    }

    try {
      setLoading(true);

      const response = await axios.get(
        `http://localhost:8080/api/vault/search?keyword=${value}`,
        {
          withCredentials: true,
        }
      );

      setResults(response.data);
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <h1>Search Vault</h1>

      <SearchBar
        value={keyword}
        onChange={handleSearch}
        placeholder="Search by title, content or tags..."
      />

      <br />
      <br />

      {loading && <p>Searching...</p>}

      {!loading &&
        results.length === 0 &&
        keyword && (
          <EmptyState
            title="No Results Found"
            description="Try another keyword."
          />
        )}

      {results.map((item) => (
        <div
          key={item.id}
          style={{
            border: "1px solid gray",
            padding: "10px",
            marginBottom: "10px",
          }}
        >
          <h3>{item.title}</h3>

          <p>
            <strong>Category:</strong>{" "}
            {item.category || "N/A"}
          </p>

          <p>
            <strong>Tags:</strong>{" "}
            {item.tags || "N/A"}
          </p>

          <p>
            <strong>Favorite:</strong>{" "}
            {item.favorite ? "Yes" : "No"}
          </p>

          <p>
            <strong>Created:</strong>{" "}
            {new Date(
              item.createdAt
            ).toLocaleString()}
          </p>
        </div>
      ))}
    </div>
  );
}

export default Search;