import React from "react";
import { Link } from "react-router-dom";

function Movies() {
  return (
    <div>
      <h1>Movies</h1>
      <p>Movies page</p>
      <Link to="/movies/1">Movie 1</Link>
    </div>
  );
}

export default Movies;
