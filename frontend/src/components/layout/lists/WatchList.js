import React from "react";
import Card from "react-bootstrap/Card";
import Tooltip from "react-bootstrap/Tooltip";
import OverlayTrigger from "react-bootstrap/OverlayTrigger";
import { BsTrash } from "react-icons/bs";

import UsersAPI from "../../../api/UsersAPI";

function WatchList({ username, name, movies, setMovies }) {
  const handleRemove = (tid) => {
    const api = new UsersAPI(username);
    api.removeWatchlist(
      tid,
      (data) => {
        if (data) {
          console.log("Removed from watchlist successfully!");
          setMovies(movies.filter((movie) => movie.tid !== tid));
        }
      },
      (error) => {
        console.log(error);
      }
    );
  };

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
                <Card.Subtitle className="mb-2 text-muted">{`Duration: ${
                  movie.duration ?? "??"
                } | Votes: ${movie.nVotes}`}</Card.Subtitle>
                <Card.Text>
                  [
                  {
                    <Card.Link
                      href={`https://www.imdb.com/title/${movie.tid}`}
                      target="_blank"
                    >
                      {movie.tid}
                    </Card.Link>
                  }
                  ]
                </Card.Text>

                <Card.Link
                  href={`/movies/${movie.tid}?username=${username}`}
                  target="_blank"
                >
                  See Details
                </Card.Link>

                <BsTrash
                  size={20}
                  className="watchlist-remove"
                  onClick={() => handleRemove(movie.tid)}
                />
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
