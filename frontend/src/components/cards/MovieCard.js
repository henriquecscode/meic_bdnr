import React from "react";
import Card from "react-bootstrap/Card";
import Button from "react-bootstrap/Button";
import { BsEye, BsEyeSlash, BsChatLeft, BsChatLeftText } from "react-icons/bs";

import movieImgHolder from "../../assets/movie-holder-img.jpg";

export default function MovieCard({ username, movie, user, cardClassName }) {
  // TODO: add and remove movie from watchlist functions

  // TODO: open modal to add interaction (vote, comment) to movie if not already added
  // otherwise show the interaction

  return (
    <Card className={"flex-row " + cardClassName} style={{ width: "18rem" }}>
      <Card.Img
        variant="top"
        src={movie.image ?? movieImgHolder}
        className="card-movie-img"
      />
      <Card.Body>
        <Card.Title className="d-flex justify-content-between">
          {movie.name}
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

        <Card.Text>{`Duration: ${movie.duration ?? "??"} | Votes: ${
          movie.nVotes ?? "--"
        } | Comments: ${movie.nComments ?? "--"}`}</Card.Text>

        <Card.Text>
          <Card.Link
            href={`/movies/${movie.tid}?username=${username}`}
            target="_blank"
          >
            See details
          </Card.Link>
        </Card.Text>

        <span className="blockquote-footer ps-2">
          <cite title="date">{`${user.name} (${user.username})`}</cite>
        </span>
      </Card.Body>
    </Card>
  );
}
