import React, { useEffect } from "react";
import Button from "react-bootstrap/Button";
import Col from 'react-bootstrap/Col';
import Container from 'react-bootstrap/Container';
import Form from "react-bootstrap/Form";
import Image from 'react-bootstrap/Image'
import InputGroup from "react-bootstrap/InputGroup";
import Row from 'react-bootstrap/Row';
import Table from 'react-bootstrap/Table';
import CommentCard from "../../components/cards/CommentCard";
import FriendCard from "../../components/cards/FriendCard";
import FilmCard from "../../components/cards/FilmCard";
import HorizontalRule from "../../components/layout/HorizontalRule";

function MovieDetails({ username }) {
  const details = [
    { field: "Name", value: "Avatar" },
    { field: "Director", value: "James Cameron" },
  ];
  const awards = [
    { name: "Academy Award for Best Cinematography", year: "2010" },
    {
      name: "Golden Globe Award for Best Motion Picture – Drama",
      year: "2010",
    },
  ];
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

  useEffect(() => {
    document.title = details.find(d => d.field == "Name").value;
  }, []);

  return (
    <div>
      <h1 className="text-center m-5">Avatar</h1>

      <Container>
        <Row className="mt-5 pb-2">
          <Col className="align-self-center text-center">
            <Image src="/logo192.png" fluid />
          </Col>
          <Col>
            <div>
              <h5 className="text-center">Details</h5>
              <Table striped bordered hover size="sm">
                <tbody>
                  {details.map((d, index) => (
                    <tr key={index}>
                      <td>{d.field}</td>
                      <td>{d.value}</td>
                    </tr>
                  ))}
                </tbody>
              </Table>
            </div>

            <div>
              <h5 className="text-center">Awards</h5>
              <Table striped bordered hover size="sm">
                <thead>
                  <tr>
                    <th>Name</th>
                    <th>Year</th>
                  </tr>
                </thead>
                <tbody>
                  {awards.map((a, index) => (
                    <tr key={index}>
                      <td>{a.name}</td>
                      <td>{a.year}</td>
                    </tr>
                  ))}
                </tbody>
              </Table>
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
                comments.map((c, index) => <CommentCard comment={c} key={index} />)
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
