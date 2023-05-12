import React from "react";
import Spinner from "react-bootstrap/Spinner";
import MovieCard from "../../cards/MovieCard";

function MoviesList({ username, movies, loading }) {
  if (loading) {
    return (
      <Spinner animation="border" variant="darkblue" className="text-center" />
    );
  }

  const totalMovies = movies.reduce(
    (total, object) => total + (object.titles ? object.titles.length : 0),
    0
  );

  return (
    <div>
      {movies && Array.isArray(movies) && movies.length > 0 ? (
        <div>
          <p>{`Found ${totalMovies} movies recommendations...`}</p>
          <div className="mb-3" style={{ display: "grid", gap: "0.75rem" }}>
            {movies.map((object) =>
              object.titles &&
              Array.isArray(object.titles) &&
              object.titles.length > 0
                ? object.titles.map((movie) => (
                    <MovieCard
                      key={movie.tid}
                      username={username}
                      movie={movie}
                      user={object.user}
                      cardClassName={"card-item w-100"}
                    />
                  ))
                : null
            )}
          </div>
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
