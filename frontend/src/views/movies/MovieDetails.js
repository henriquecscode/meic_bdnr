import React, { useEffect, useState, useRef } from "react";
import Button from "react-bootstrap/Button";
import Col from "react-bootstrap/Col";
import Container from "react-bootstrap/Container";
import Form from "react-bootstrap/Form";
import Image from "react-bootstrap/Image";
import InputGroup from "react-bootstrap/InputGroup";
import Row from "react-bootstrap/Row";
import Card from "react-bootstrap/Card";
import CommentCard from "../../components/cards/CommentCard";
import FriendCard from "../../components/cards/FriendCard";
import FilmCard from "../../components/cards/FilmCard";
import HorizontalRule from "../../components/layout/HorizontalRule";
import { BsFillStarFill } from "react-icons/bs";

import MoviesAPI from "../../api/MoviesAPI";
import UsersAPI from "../../api/UsersAPI";

function MovieDetails({ username, id }) {
  const [filmDetails, setFilmDetails] = useState([]);
  const [filmAwards, setFilmAwards] = useState([]);
  const [filmWorkers, setFilmWorkers] = useState([]);
  const [usersWatched, setUsersWatched] = useState([]);
  const [genres, setGenres] = useState([]);
  const [filmSeries, setFilmSeries] = useState([]);
  const formRef = useRef(null);

  useEffect(() => {
    const api = new MoviesAPI();

    const urlId =
      id === undefined ? window.location.pathname.replace("/movies/", "") : id;

    // Details
    api.getFilm(
      urlId,
      (json) => {
        setFilmAwards(json.title.awards.awards);

        let details = json.title;
        for (let key of ["nComments", "nVotes", "awards"]) {
          delete details[key];
        }

        setFilmDetails({ ...details, rating: json.avgVote });
        setFilmWorkers(json.roles);
        setUsersWatched(json.watched);
        setGenres(json.genres);
        setFilmSeries(json.series);

        document.title = "FilmFriend - " + details.name;
      },
      (error) => {
        setFilmDetails([]);
        setFilmWorkers([]);
        setFilmAwards([]);
        setUsersWatched([]);
        setGenres([]);
        setFilmSeries([]);
        console.log(error);
      }
    );
  }, [id]);

  const submitWatchForm = (e) => {
    e.preventDefault();

    const api = new UsersAPI(username);

    let watched = {
      comment: e.target.comment.value,
      vote: e.target.vote.value,
      date: new Date().toISOString(),
    };

    if (id === undefined) id = window.location.pathname.replace("/movies/", "");

    api.addWatched(
      id,
      (_) => {
        const api = new MoviesAPI();

        // Update watched users
        api.getFilm(
          id,
          (json) => {
            setUsersWatched(json.watched);
          },
          (error) => {
            console.log(error);
          }
        );
      },
      (error) => {
        console.log(error);
      },
      watched
    );

    // Clear form
    formRef.current.reset();
  };

  const removeWatch = () => {
    const api = new UsersAPI(username);
    if (id === undefined) id = window.location.pathname.replace("/movies/", "");

    api.removeWatched(
      id,
      (json) => {
        const api = new MoviesAPI();

        // Update watched users
        api.getFilm(
          id,
          (json) => {
            setUsersWatched(json.watched);
          },
          (error) => {
            console.log(error);
          }
        );
      },
      (error) => {
        console.log(error);
      }
    );
  };

  const getRating = () => {
    return (
      <p className="d-flex mb-0 fw-bold">
        {filmDetails.rating && filmDetails.ratting !== "NaN"
          ? filmDetails.rating
          : "--"}
        /10
        <BsFillStarFill size={25} className="ms-2 color-highlight" />
      </p>
    );
  };

  const getDetailsList = () => {
    let list = [];

    let i = 0;
    for (const [key, value] of Object.entries(filmDetails)) {
      if (value === null || value.length === 0) continue;

      if (value instanceof Array) {
        list.push(
          <p key={i} className="mb-0">
            <b>{key}</b>: {value.map((e) => e.name)}
          </p>
        );
      } else {
        list.push(
          <p key={i} className="mb-0">
            <b>{key}</b>:&nbsp;
            {key === "tid" ? (
              <Card.Link
                href={`https://www.imdb.com/title/${value}`}
                target="_blank"
              >
                {value}
              </Card.Link>
            ) : (
              value
            )}
          </p>
        );
      }
      i++;
    }

    // Genres
    list.push(
      <p key={"genres"} className="mb-0">
        <b>{"genres"}</b>:{" "}
        {genres.map((e, index) => (
          <span key={index}>{e.name !== "" ? e.name + ", " : ""}</span>
        ))}
      </p>
    );

    return list;
  };

  const getWorkersList = () => {
    // TODO: show entityAwards in tooltip if we have this info in endpoint
    let list = [];

    filmWorkers.forEach((worker, index) => {
      list.push(
        <span key={index}>
          <b>
            <Card.Link
              href={`https://www.imdb.com/name/${worker.worker.nid}`}
              target="_blank"
            >
              {worker.worker.name}
            </Card.Link>
          </b>
          {" ("}
          {worker.role.type ?? "Unknown role"}
          {"), "}
        </span>
      );
    });

    return list;
  };

  const getComments = () => {
    let list = [];

    usersWatched.forEach((user) => {
      if (user.watched.comment)
        list.push({
          text: user.watched.comment,
          author: user.user,
          vote: user.watched.vote,
          date: user.watched.date,
        });
    });

    list.sort((a, b) => {
      return a.date < b.date ? -1 : 1;
    });

    return list;
  };

  let comments = getComments();

  return (
    <div>
      <div className="profile-info">
        <Container className="py-5">
          <h2 className="text-center">{filmDetails.name}</h2>
        </Container>
      </div>
      <Container className="py-5">
        <Row>
          <Col sm={4} className="text-center">
            <Image src="/movie.svg" fluid className="film-img" />
          </Col>

          <Col sm={8}>
            <div className="d-flex justify-content-between">
              <h5>Details</h5>
              {getRating()}
            </div>
            <div className="mb-4">{getDetailsList()}</div>

            <h5>Workers</h5>
            <div className="mb-4">{getWorkersList()}</div>

            <h5>Awards</h5>
            {filmAwards &&
            Array.isArray(filmAwards) &&
            filmAwards.length > 0 ? (
              <div>
                {filmAwards.map((a, index) => (
                  <p key={index} className="mb-0">
                    {a.awardName}{" "}
                    <i>
                      ({a.receivedOn === "" ? "??" : a.receivedOn.split("T")[0]}
                      )
                    </i>
                  </p>
                ))}
              </div>
            ) : (
              <p>
                <i>No awards</i>
              </p>
            )}
          </Col>
        </Row>

        <HorizontalRule text={"Social"} />

        <Row className="my-2">
          <Col md={3} className="py-2 bg-light">
            <h5>People that watched</h5>
            {usersWatched &&
            Array.isArray(usersWatched) &&
            usersWatched.length > 0 ? (
              <div
                className="pt-2"
                style={{ display: "grid", gridGap: "0.5rem" }}
              >
                {usersWatched.map((user, index) => (
                  <FriendCard
                    friend={user.user}
                    voteInfo={user.watched.vote}
                    key={index}
                  />
                ))}
              </div>
            ) : (
              <p>
                <i>No users have watched</i>
              </p>
            )}
          </Col>

          <Col md={9} className="py-2 bg-light border-start">
            <h5>Comments</h5>
            {comments && Array.isArray(comments) && comments.length > 0 ? (
              <div>
                {comments.map((c, index) => (
                  <CommentCard
                    comment={c}
                    key={index}
                    withDelete={c.author.username === username}
                    onDelete={() =>
                      c.author.username === username ? removeWatch() : null
                    }
                  />
                ))}
              </div>
            ) : (
              <p>
                <i>No comments</i>
              </p>
            )}

            <Form
              ref={formRef}
              className="pt-3 bd-highlight"
              onSubmit={submitWatchForm}
            >
              <InputGroup className="mb-3">
                <div style={{ flexGrow: 5 }}>
                  <Form.Control
                    name="comment"
                    aria-label="Comment"
                    placeholder="Comment something..."
                  />
                  <Form.Text className="text-muted">
                    To only mark as watched, just submit with empty fields. To
                    update a review, just write a new one.
                  </Form.Text>
                </div>

                <div className="px-2">
                  <Form.Control
                    name="vote"
                    aria-label="Vote"
                    placeholder="Vote"
                    type="number"
                    min={0}
                    max={10}
                  />
                </div>

                <div>
                  <Button type="submit" variant="darkblue">
                    Submit
                  </Button>
                </div>
              </InputGroup>
            </Form>
          </Col>
        </Row>

        <HorizontalRule
          text={`Series: ${
            filmSeries && filmSeries.series ? filmSeries.series.name : "---"
          }`}
        />

        <Row className="py-2">
          {filmSeries &&
          filmSeries.titles &&
          Array.isArray(filmSeries.titles) &&
          filmSeries.titles.length > 0 ? (
            filmSeries.titles.map((movie, index) => (
              <Col sm={6} md={4} lg={3} className="px-1" key={index}>
                <FilmCard
                  key={index}
                  film={movie.title}
                  username={username}
                  detailsTopRight={false}
                />
              </Col>
            ))
          ) : (
            <p>
              <i>No other films in the same Serie</i>
            </p>
          )}
        </Row>
      </Container>
    </div>
  );
}

export default MovieDetails;
