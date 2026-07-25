import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import logo from "../assets/personal vault.png";

function Navbar() {
  const navigate = useNavigate();

  const fullName =
    localStorage.getItem("fullName");

  const handleLogout = async () => {
    try {
      await axios.post(
        "http://localhost:8080/api/auth/logout",
        {},
        {
          withCredentials: true,
        }
      );

      localStorage.clear();

      navigate("/login");
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <nav className="navbar">

      <div className="navbar-logo">
        <Link to="/" className="logo-link">
          <img
            src={logo}
            alt="KeepSpace Logo"
            className="logo-image"
          />

          <h2>KeepSpace</h2>
        </Link>
      </div>

      <div className="navbar-user">

        <div className="user-info">
          <span className="welcome-text">
            
          </span>

          <strong>
            {fullName || "User"}
          </strong>
        </div>

        <button
          className="logout-btn"
          onClick={handleLogout}
        >
          Logout
        </button>

      </div>

    </nav>
  );
}

export default Navbar;