import "bootstrap/dist/css/bootstrap.min.css";
import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

import NavBar from "./components/layout/navigation/NavBar.js";
import Profile from "./views/profile/Profile.js";
import Movies from "./views/movies/Movies.js";
import MovieDetails from "./views/movies/MovieDetails.js";
import Recommendations from "./views/recommendations/Recommendations.js";
import Analytics from "./views/analytics/Analytics.js";
import Users from "./views/profile/Users.js";

function App() {
  const queryParams = new URLSearchParams(window.location.search);
  const username = queryParams.has("username")
    ? queryParams.get("username")
    : "username1";

  return (
    <div>
      <Router>
        <NavBar username={username} />

        <Routes>
          <Route path="/profile" element={<Profile username={username} />} />
          <Route path="/movies" element={<Movies username={username} />} />
          <Route
            path="/movies/:id"
            element={<MovieDetails username={username} />}
          />
          <Route
            path="/recommendations"
            element={<Recommendations username={username} />}
          />
          <Route
            path="/analytics"
            element={<Analytics username={username} />}
          />
          <Route path="/users" element={<Users username={username} />} />
          <Route path="*" element={<Movies username={username} />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
