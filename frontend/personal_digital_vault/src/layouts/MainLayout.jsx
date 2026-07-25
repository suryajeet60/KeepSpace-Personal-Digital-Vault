import { Outlet } from "react-router-dom";
import Navbar from "../components/Navbar";
import Sidebar from "../components/Sidebar";
import Footer from "../components/Footer";

function MainLayout() {
  return (
    <div className="layout">

      <Navbar />

      <div className="layout-body">

        <Sidebar />

        <main className="layout-content">
          <Outlet />
        </main>

      </div>

      <Footer />

    </div>
  );
}

export default MainLayout;