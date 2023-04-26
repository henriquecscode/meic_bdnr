import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

import NavBar from "./components/layout/navigation/NavBar.js";
import Login from "./views/authentication/Login.js";
import Profile from "./views/profile/Profile.js";
import Movies from "./views/movies/Movies.js";
import MovieDetails from "./views/movies/MovieDetails.js";
import Recommendations from "./views/recommendations/Recommendations.js";
import Analytics from "./views/analytics/Analytics.js";

function App() {
  return (
    <div>
      <Router>
        <NavBar />

        <Routes>
          <Route exact path="/" element={<Login />} />
          <Route path="/profile" element={<Profile />} />
          <Route path="/movies" element={<Movies />} />
          <Route path="/movies/:id" element={<MovieDetails />} />
          <Route path="/recommendations" element={<Recommendations />} />
          <Route path="/analytics" element={<Analytics />} />
          <Route path="*" element={<Login />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
