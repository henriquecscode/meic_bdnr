import React from "react";
import Card from "react-bootstrap/Card";
// import Button from "react-bootstrap/Button";
import Tooltip from "react-bootstrap/Tooltip";
import OverlayTrigger from "react-bootstrap/OverlayTrigger";

function WatchList({ username, name, movies }) {
  // TODO: change remove button - to options to remove but also to add to watched list (add interaction)

  // const handleRemove = (id) => {
  //   // TODO: remove movie from watchlist
  //   console.log(id);
  // };

  return (
    <div>
      <h4 className="mb-3">{name}</h4>

      {movies && Array.isArray(movies) && movies.length > 0 ? (
        <div className="cards-container mb-3">
          {movies.map((movie) => (
            <Card key={movie.tid} className="card-item">
              {movie.image !== undefined && movie.image !== null && (
                <Card.Img variant="top" src={movie.image} alt={movie.name} />
              )}
              <Card.Body>
                <OverlayTrigger
                  placement="top"
                  overlay={<Tooltip id="title-tooltip">{movie.name}</Tooltip>}
                >
                  <Card.Title className="line-ellipsis">
                    {movie.name}
                  </Card.Title>
                </OverlayTrigger>
                <Card.Subtitle className="mb-2 text-muted">{`Duration: ${movie.duration ?? "??"
                  } | Votes: ${movie.nVotes}`}</Card.Subtitle>
                <Card.Text>[{<Card.Link href={`https://www.imdb.com/title/${movie.tid}`}
                  target="_blank" >{movie.tid}</Card.Link>}]</Card.Text>

                <Card.Link
                  href={`/movies/${movie.tid}?username=${username}`}
                  target="_blank"
                >
                  See Details
                </Card.Link>

                {/* <Button
                  variant="danger"
                  size="sm"
                  onClick={() => handleRemove(movie.tid)}
                >
                  Remove
                </Button> */}
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
