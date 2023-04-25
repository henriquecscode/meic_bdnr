import React from "react";
import { Link } from "react-router-dom";

function NavBar() {
  return (
    <div>
      <ul>
        <li>
          <Link to="/">login</Link>
        </li>
        <li>
          <Link to="/analytics">analytics</Link>
        </li>
        <li>
          <Link to="/recommendations">recomendations</Link>
        </li>
        <li>
          <Link to="/movies">movies</Link>
        </li>
        <li>
          <Link to="/profile">profile</Link>
        </li>
      </ul>
    </div>
  );
}

export default NavBar;
