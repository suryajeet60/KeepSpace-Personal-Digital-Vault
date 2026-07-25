import { Link } from "react-router-dom";
import logo from "../assets/personal vault.png";
import {
  FaGithub,
  FaDatabase,
  FaReact,
  FaJava,
} from "react-icons/fa";

function Footer() {
  const year = new Date().getFullYear();

  return (
    <footer className="footer">
      <div className="footer-container">

        {/* Brand */}
        <div className="footer-section">
          <div className="footer-logo">
            <img src={logo} alt="KeepSpace Logo" />
            <h2>KeepSpace</h2>
          </div>

          <p>
            Securely organize your personal digital vault for 
            notes, ideas, code snippets, learning resources, 
            project references and more.
          </p>

        </div>

        {/* Quick Links */}
        <div className="footer-section">
          <h3>Quick Links</h3>

          <Link to="/">Dashboard</Link>
          <Link to="/vault">Vault</Link>
          <Link to="/add">Add Item</Link>
          <Link to="/search">Search</Link>
          <Link to="/analytics">Analytics</Link>
        </div>

        {/* Features */}
        <div className="footer-section">
          <h3>Features</h3>

          <p>Smart Organization</p>
          <p>Advanced Search</p>
          <p>Favorites</p>
          <p>Analytics</p>
          <p>Secure Storage</p>
        </div>

        {/* Services */}
        <div className="footer-section">
          <h3>Services</h3>

          <p>Digital Vault</p>
          <p>Notes Management</p>
          <p>Reference Library</p>
          <p>Personal Archive</p>
        </div>

      </div>

      <div className="footer-bottom">
        © 2025 KeepSpace | Personal Digital Vault | Developed by Suryajeet Pandhare
      </div>
    </footer>
  );
}

export default Footer;
