import { Link, useLocation } from "react-router-dom";

function Sidebar() {
  const location = useLocation();

  const menuItems = [
    {
      path: "/",
      label: "Dashboard",
    },
    {
      path: "/vault",
      label: "My Vault",
    },
    {
      path: "/add",
      label: "Add Item",
    },
    {
      path: "/favorites",
      label: "Favorites",
    },
    {
      path: "/archived",
      label: "Archived",
    },
    {
      path: "/search",
      label: "Search",
    },
    {
      path: "/analytics",
      label: "Analytics",
    },
  ];

  return (
    <aside className="sidebar">
      <div className="sidebar-header">
        <h3>Workspace</h3>
      </div>

      <ul>
        {menuItems.map((item) => (
          <li
            key={item.path}
            className={
              location.pathname === item.path
                ? "active"
                : ""
            }
          >
            <Link to={item.path}>
              {item.label}
            </Link>
          </li>
        ))}
      </ul>
    </aside>
  );
}

export default Sidebar;