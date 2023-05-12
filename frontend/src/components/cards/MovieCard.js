import React from "react";
import Card from "react-bootstrap/Card";
import Button from "react-bootstrap/Button";
import { BsEye, BsEyeSlash, BsChatLeft, BsChatLeftText } from "react-icons/bs";

export default function MovieCard({ username, movie, cardClassName }) {
  // TODO: add and remove movie from watchlist functions

  // TODO: open modal to add interaction (vote, comment) to movie if not already added
  // otherwise show the interaction

  return (
    <Card className={"flex-row " + cardClassName} style={{ width: "18rem" }}>
      <Card.Img
        variant="top"
        src="holder.js/100px180"
        className="card-movie-img"
      />
      <Card.Body>
        <Card.Title className="d-flex justify-content-between">
          {movie.title}
          <div>
            <Button
              variant={movie.watchlist ? "darkblue" : "light"}
              size="sm"
              className="me-2"
            >
              {movie.watchlist ? <BsEye size={20} /> : <BsEyeSlash size={20} />}
            </Button>

            <Button
              variant={movie.watched ? "darkblue" : "light"}
              size="sm"
              className="me-2"
            >
              {movie.watched ? (
                <BsChatLeftText size={20} />
              ) : (
                <BsChatLeft size={20} />
              )}
            </Button>
          </div>
        </Card.Title>
        <Card.Subtitle className="mb-2 text-muted">{`Genre: ${movie.genre}`}</Card.Subtitle>
        <Card.Text>{movie.description}</Card.Text>
        <Card.Link href={`/movies/${movie.id}?username=${username}`}>
          See details
        </Card.Link>
      </Card.Body>
    </Card>
  );
}
