import { Link } from "react-router-dom";
import { FaRocket, FaPlus, FaFolderOpen } from "react-icons/fa";

function HeroSection({
  userName = "User",
  totalItems = 0,
  favorites = 0,
  highPriority = 0,
}) {
  return (
    <div className="hero-section">
      <div className="hero-content">
        <h1>
          👋 Welcome Back, {userName}
        </h1>

        <p>
          You have <strong>{totalItems}</strong> saved resources,
          <strong> {favorites}</strong> favorites, and
          <strong> {highPriority}</strong> high-priority notes.
        </p>

        <span className="hero-message">
          <FaRocket />
          Keep learning and building
        </span>

        <div className="hero-actions">
          <Link to="/add" className="hero-btn primary">
            <FaPlus />
            Add Item
          </Link>

          <Link to="/vault" className="hero-btn secondary">
            <FaFolderOpen />
            View Vault
          </Link>
        </div>

        {/* Mini Statistics Cards */}

        <div className="hero-stats">

          <div className="hero-stat">
            <h3>{totalItems}</h3>
            <p>Total Items</p>
          </div>

          <div className="hero-stat">
            <h3>{favorites}</h3>
            <p>Favorites</p>
          </div>

          <div className="hero-stat">
            <h3>{highPriority}</h3>
            <p>High Priority</p>
          </div>

        </div>

      </div>
    </div>
  );
}

export default HeroSection;
