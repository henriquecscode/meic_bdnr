import React from "react";
import Card from "react-bootstrap/Card";
import Button from "react-bootstrap/Button";

function WatchList({ name, movies }) {
  const handleRemove = (id) => {
    // TODO: remove movie from watchlist
    console.log(id);
  };

  return (
    <div>
      <h4 className="mb-3">{name}</h4>

      {movies && Array.isArray(movies) && movies.length > 0 ? (
        <div className="cards-container mb-3">
          {movies.map((movie) => (
            <Card key={movie.id} className="card-item">
              <Card.Img variant="top" src={movie.image} alt={movie.title} />
              <Card.Body>
                <Card.Title>{movie.title}</Card.Title>
                <Card.Subtitle className="mb-2 text-muted">{`Genre: ${movie.genre}`}</Card.Subtitle>
                <Card.Text>Some Description...</Card.Text>
                <Button
                  variant="danger"
                  size="sm"
                  onClick={() => handleRemove(movie.id)}
                >
                  Remove
                </Button>
              </Card.Body>
            </Card>
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

export default WatchList;
