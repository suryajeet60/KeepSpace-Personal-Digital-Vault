import { Routes, Route } from "react-router-dom";

import MainLayout from "../layouts/MainLayout";

import ProtectedRoute from "../components/ProtectedRoute";

import Login from "../pages/Login";
import Register from "../pages/Register";

import Dashboard from "../pages/Dashboard";
import Vault from "../pages/Vault";
import AddItem from "../pages/AddItem";
import ViewItem from "../pages/ViewItem";
import EditItem from "../pages/EditItem";
import Search from "../pages/Search";
import Analytics from "../pages/Analytics";
import Favorites from "../pages/Favorites";
import Archived from "../pages/Archived";

import NotFound from "../pages/NotFound";

function AppRoutes() {
  return (
    <Routes>

      {/* Public Routes */}

      <Route
        path="/login"
        element={<Login />}
      />

      <Route
        path="/register"
        element={<Register />}
      />

      {/* Protected Routes */}

      <Route
        element={
          <ProtectedRoute>
            <MainLayout />
          </ProtectedRoute>
        }
      >
        <Route
          path="/"
          element={<Dashboard />}
        />

        <Route
          path="/vault"
          element={<Vault />}
        />

        <Route
          path="/add"
          element={<AddItem />}
        />

        <Route
          path="/item/:id"
          element={<ViewItem />}
        />

        <Route
          path="/edit/:id"
          element={<EditItem />}
        />

        <Route
          path="/favorites"
          element={<Favorites />}
        />

        <Route
          path="/archived"
          element={<Archived />}
        />

        <Route
          path="/search"
          element={<Search />}
        />

        <Route
          path="/analytics"
          element={<Analytics />}
        />
      </Route>

      {/* 404 */}

      <Route
        path="*"
        element={<NotFound />}
      />

    </Routes>
  );
}

export default AppRoutes;