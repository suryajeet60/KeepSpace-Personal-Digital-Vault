import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function AddItem() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    title: "",
    content: "",
    category: "",
    tags: "",
    referenceUrl: "",
    priority: "Low",
    favorite: false,
    archived: false,
  });

  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;

    setFormData({
      ...formData,
      [name]:
        type === "checkbox"
          ? checked
          : value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      setLoading(true);
      setMessage("");

      await axios.post(
        "http://localhost:8080/api/vault",
        formData,
        {
          withCredentials: true,
        }
      );

      setMessage("Item Created Successfully");

      setTimeout(() => {
        navigate("/vault");
      }, 1000);

    } catch (error) {
      console.error(error);

      setMessage(
        error.response?.data?.message ||
          "Failed to create item"
      );
    } finally {
      setLoading(false);
    }
  };

  return (
  <div className="form-container">
    <h1 className="page-title">Add New Item</h1>

    {message && <p>{message}</p>}

    <form onSubmit={handleSubmit}>

      <div className="form-group">
        <label>Title</label>
        <input
          className="form-control"
          type="text"
          name="title"
          value={formData.title}
          onChange={handleChange}
          required
        />
      </div>

      <div className="form-group">
        <label>Content</label>
        <textarea
          className="form-control"
          name="content"
          rows="5"
          value={formData.content}
          onChange={handleChange}
        />
      </div>

      <div className="form-group">
        <label>Category</label>
        <input
          className="form-control"
          type="text"
          name="category"
          value={formData.category}
          onChange={handleChange}
        />
      </div>

      <div className="form-group">
        <label>Tags</label>
        <input
          className="form-control"
          type="text"
          name="tags"
          placeholder=""
          value={formData.tags}
          onChange={handleChange}
        />
      </div>

      <div className="form-group">
        <label>Reference URL</label>
        <input
          className="form-control"
          type="text"
          name="referenceUrl"
          value={formData.referenceUrl}
          onChange={handleChange}
        />
      </div>

      <div className="form-group">
        <label>Priority</label>
        <select
          className="form-control"
          name="priority"
          value={formData.priority}
          onChange={handleChange}
        >
          <option value="Low">Low</option>
          <option value="Medium">Medium</option>
          <option value="High">High</option>
        </select>
      </div>

      <div className="form-group">
        <label>
          <input
            type="checkbox"
            name="favorite"
            checked={formData.favorite}
            onChange={handleChange}
          />
          {" "}Favorite
        </label>
      </div>

      <div className="form-group">
        <label>
          <input
            type="checkbox"
            name="archived"
            checked={formData.archived}
            onChange={handleChange}
          />
          {" "}Archived
        </label>
      </div>

      <button
        className="submit-btn"
        type="submit"
        disabled={loading}
      >
        {loading ? "Saving..." : "Save Item"}
      </button>

    </form>
  </div>
);
}

export default AddItem;