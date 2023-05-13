import React from "react";
import Card from "react-bootstrap/Card";
import Button from "react-bootstrap/Button";
import { BsEye, BsEyeSlash } from "react-icons/bs";

import movieImgHolder from "../../assets/movie-holder-img.jpg";

export default function MovieCard({
  username,
  movie,
  user,
  inWatchlist,
  onAddWatchlistClick = () => {},
  onRemoveWatchlistClick = () => {},
  cardClassName,
}) {
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
              variant={inWatchlist ? "darkblue" : "light"}
              size="sm"
              className="me-2"
              onClick={() => {
                inWatchlist
                  ? onRemoveWatchlistClick(movie)
                  : onAddWatchlistClick(movie);
              }}
            >
              {inWatchlist ? <BsEye size={20} /> : <BsEyeSlash size={20} />}
            </Button>
          </div>
        </Card.Title>

        <Card.Subtitle className="mb-2 text-muted">{`TID: ${movie.tid}`}</Card.Subtitle>

        <Card.Text>{`Duration: ${movie.duration ?? "??"} | Nº Votes: ${
          movie.nVotes ?? "--"
        } | Nº Comments: ${movie.nComments ?? "--"}`}</Card.Text>

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
