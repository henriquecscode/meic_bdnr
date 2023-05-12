import React, { useEffect, useState } from "react";
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
import MoviesAPI from "../../api/MoviesAPI";

function MovieDetails({ username, id }) {
  const [filmDetails, setFilmDetails] = useState([]);
  const [filmAwards, setFilmAwards] = useState([]);
  const [filmWorkers, setFilmWorkers] = useState([]);
  // const [filmSeries, setFilmSeries] = useState([]); // TODO: outside title, called "series"
  // const [usersWatched, setUsersWatched] = useState([]); // TODO: outside title, called "watched"

  const series = [
    { id: 1, name: "Avatar", nr: 1 },
    { id: 2, name: "Avatar: Way of Water", nr: 2 },
  ];
  const comments = [
    { text: "Amazing", author: "Catarina" },
    { text: "BEST MOVIE EVER!", author: "John Doe" },
  ];
  const users = [
    { username: "Catarina" },
    { username: "Henrique" },
    { username: "Patricia" },
    { username: "John Doe" },
  ];

  const notDetailsProperties = ["nComments", "nVotes", "awards"]; // inside title

  useEffect(() => {
    const api = new MoviesAPI();

    if (id === undefined) id = window.location.pathname.replace("/movies/", "");

    // Details
    api.getFilm(
      id,
      (json) => {
        setFilmAwards(json.title.awards.awards);

        let details = json.title;
        for (let key of notDetailsProperties) {
          delete details[key];
        }

        setFilmDetails(details);

        setFilmWorkers(json.roles);

        document.title = "FilmFriend - " + details.name;
      },
      (error) => {
        setFilmDetails([]);
        setFilmWorkers([]);
        setFilmAwards([]);
        console.log(error);
      }
    );
  }, []);

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
            {key === "tid" ?
              <Card.Link href={`https://www.imdb.com/title/${value}`} target="_blank" >{value}</Card.Link>
              : value
            }
          </p>
        );
      }
      i++;
    }

    return list;
  };

  const getWorkersList = () => {
    let list = [];

    filmWorkers.forEach((worker) => {
      list.push(
        <p key={`workers-list${worker.worker.nid}`} className="mb-0">
          <b><Card.Link
            href={`https://www.imdb.com/name/${worker.worker.nid}`}
            target="_blank"
          >
            {worker.worker.name}
          </Card.Link></b>: {worker.role.type ?? "Unknown role"}
        </p>
      );
    })

    return list;
  };


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
            <h5>Details</h5>
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
                    {a.awardName} <i>({a.receivedOn})</i>
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
          <Col sm={3} className="py-2 bg-light">
            <h5>People that watched</h5>
            {users && Array.isArray(users) && users.length > 0 ? (
              <div
                className="pt-2"
                style={{ display: "grid", gridGap: "0.5rem" }}
              >
                {users.map((friend, index) => (
                  <FriendCard friend={friend} key={index} />
                ))}
              </div>
            ) : (
              <p>
                <i>No users have watched</i>
              </p>
            )}
          </Col>

          <Col sm={9} className="py-2 bg-light border-start">
            <h5>Comments</h5>
            {comments && Array.isArray(comments) && comments.length > 0 ? (
              <div>
                {comments.map((c, index) => (
                  <CommentCard comment={c} key={index} />
                ))}
              </div>
            ) : (
              <p>
                <i>No comments</i>
              </p>
            )}

            <Form className="pt-3 bd-highlight">
              <InputGroup className="mb-3">
                <Form.Control
                  aria-label="Comment"
                  placeholder="Comment something..."
                />
                <Button type="submit" variant="darkblue">
                  Comment
                </Button>
              </InputGroup>
            </Form>
          </Col>
        </Row>

        <HorizontalRule text={"Series"} />

        <Row className="py-2">
          <div className="d-flex flex-wrap">
            {series.map((s, index) => (
              <div className="px-1 w-25">
                <FilmCard
                  key={index}
                  film={s}
                  username={username}
                  detailsTopRight={false}
                />
              </div>
            ))}
          </div>
        </Row>
      </Container>
    </div>
  );
}

export default MovieDetails;
