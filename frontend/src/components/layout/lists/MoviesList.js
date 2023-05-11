import React from "react";
import Spinner from "react-bootstrap/Spinner";
import MovieCard from "../../cards/MovieCard";

function MoviesList({ username, movies, loading }) {
  if (loading) {
    return (
      <Spinner animation="border" variant="darkblue" className="text-center" />
    );
  }

  return (
    <div>
      {movies && Array.isArray(movies) && movies.length > 0 ? (
        <div className="mb-3" style={{ display: "grid", gap: "0.75rem" }}>
          {movies.map((movie) => (
            <MovieCard
              key={movie.id}
              username={username}
              movie={movie}
              cardClassName={"card-item w-100"}
            />
          ))}
        </div>
      ) : (
        <p>
          <i>No movies to list</i>
        </p>
      )}
    </div>
  );
}

export default MoviesList;
