import { useEffect, useState } from "react";
import {
  useNavigate,
  useParams,
} from "react-router-dom";
import axios from "axios";

function EditItem() {
  const { id } = useParams();

  const navigate = useNavigate();

  const [loading, setLoading] =
    useState(true);

  const [message, setMessage] =
    useState("");

  const [formData, setFormData] =
    useState({
      title: "",
      content: "",
      category: "",
      tags: "",
      referenceUrl: "",
      priority: "Low",
      favorite: false,
      archived: false,
    });

  useEffect(() => {
    fetchItem();
  }, []);

  const fetchItem = async () => {
    try {
      const response = await axios.get(
        `http://localhost:8080/api/vault/${id}`,
        {
          withCredentials: true,
        }
      );

      setFormData(response.data);
    } catch (error) {
      console.error(error);
      setMessage(
        "Failed to load item"
      );
    } finally {
      setLoading(false);
    }
  };

  const handleChange = (e) => {
    const {
      name,
      value,
      type,
      checked,
    } = e.target;

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
      await axios.put(
        `http://localhost:8080/api/vault/${id}`,
        {
          title: formData.title,
          content: formData.content,
          category: formData.category,
          tags: formData.tags,
          referenceUrl:
            formData.referenceUrl,
          priority: formData.priority,
          favorite: formData.favorite,
          archived: formData.archived,
        },
        {
          withCredentials: true,
        }
      );

      setMessage(
        "Item Updated Successfully"
      );

      setTimeout(() => {
        navigate("/vault");
      }, 1000);

    } catch (error) {
      console.error(error);

      setMessage(
        error.response?.data?.message ||
          "Update Failed"
      );
    }
  };

  if (loading) {
    return <h2>Loading Item...</h2>;
  }

  return (
  <div className="form-container">
    <h1 className="page-title">Edit Item</h1>

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
          rows="5"
          name="content"
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
      >
        Update Item
      </button>

    </form>
  </div>
);
}

export default EditItem;