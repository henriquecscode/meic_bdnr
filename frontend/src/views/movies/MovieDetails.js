import React, { useEffect, useState } from "react";
import Button from "react-bootstrap/Button";
import Col from "react-bootstrap/Col";
import Container from "react-bootstrap/Container";
import Form from "react-bootstrap/Form";
import Image from "react-bootstrap/Image";
import InputGroup from "react-bootstrap/InputGroup";
import Row from "react-bootstrap/Row";
import Table from "react-bootstrap/Table";
import CommentCard from "../../components/cards/CommentCard";
import FriendCard from "../../components/cards/FriendCard";
import FilmCard from "../../components/cards/FilmCard";
import HorizontalRule from "../../components/layout/HorizontalRule";
import MoviesAPI from "../../api/MoviesAPI";

function MovieDetails({ username, id }) {
  const [filmDetails, setFilmDetails] = useState([]);
  const [filmAwards, setFilmAwards] = useState([]);
  const [filmSeries, setFilmSeries] = useState([]); // TODO: outside title, called "series"
  const [usersWatched, setUsersWatched] = useState([]); // TODO: outside title, called "watched"

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

        document.title = details.name;
      },
      (error) => {
        setFilmDetails([]);
        console.log(error);
      }
    );
  }, []);

  const getDetailsTable = () => {
    let table = [];

    let i = 0;
    for (const [key, value] of Object.entries(filmDetails)) {
      if (value === null || value.length === 0) continue;

      if (value instanceof Array) {
        table.push(
          <tr key={i}>
            <td>{key}</td>
            <td>{value.map((e) => e.name)}</td>
          </tr>
        );
      } else {
        table.push(
          <tr key={i}>
            <td>{key}</td>
            <td>{value}</td>
          </tr>
        );
      }
      i++;
    }
    return table;
  };

  return (
    <div>
      <h1 className="text-center m-5">{filmDetails.name}</h1>

      <Container>
        <Row className="mt-5 pb-2">
          <Col className="align-self-center text-center">
            <Image src="/movie.svg" fluid className="w-50" />
          </Col>
          <Col>
            <div>
              <h5 className="text-center">Details</h5>
              <Table striped bordered hover size="sm">
                <tbody>{getDetailsTable()}</tbody>
              </Table>
            </div>

            <div>
              <h5 className="text-center">Awards</h5>
              {filmAwards &&
              Array.isArray(filmAwards) &&
              filmAwards.length === 0 ? (
                <p className="text-center">
                  <i>No awards</i>
                </p>
              ) : (
                <Table striped bordered hover size="sm">
                  <thead>
                    <tr>
                      <th>Name</th>
                      <th>Year</th>
                    </tr>
                  </thead>
                  <tbody>
                    {filmAwards.map((a, index) => (
                      <tr key={index}>
                        <td>{a.awardName}</td>
                        <td>{a.receivedOn}</td>
                      </tr>
                    ))}
                  </tbody>
                </Table>
              )}
            </div>
          </Col>
        </Row>

        <HorizontalRule text={"Social"} />

        <Row className="mt-5 pb-2 pt-4 pb-4">
          <Col
            className="d-flex flex-column align-items-center bg-light rounded-2"
            style={{ marginRight: "10px" }}
          >
            <h4 className="text-center">People that watched</h4>
            {users && Array.isArray(users) && users.length > 0 ? (
              users.map((friend, index) => (
                <FriendCard friend={friend} key={index} />
              ))
            ) : (
              <p>
                <i>No users have watched</i>
              </p>
            )}
          </Col>
          <Col className="bg-light rounded-2">
            <h4 className="text-center">Comments</h4>
            <div className="d-flex flex-column bd-highlight mb-3">
              {comments && Array.isArray(comments) && comments.length > 0 ? (
                comments.map((c, index) => (
                  <CommentCard comment={c} key={index} />
                ))
              ) : (
                <p>
                  <i>No comments</i>
                </p>
              )}

              <Form className="p-2 bd-highlight">
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
            </div>
          </Col>
        </Row>

        <HorizontalRule text={"Series"} />

        <Row className="mt-5 pb-2">
          <Col>
            <div className="d-flex flex-row bd-highlight mb-3">
              {series.map((s, index) => (
                <FilmCard key={index} film={s} username={username} />
              ))}
            </div>
          </Col>
        </Row>
      </Container>
    </div>
  );
}

export default MovieDetails;
