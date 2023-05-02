import React from "react";
import Col from 'react-bootstrap/Col';
import Container from 'react-bootstrap/Container';
import Image from 'react-bootstrap/Image'
import Row from 'react-bootstrap/Row';
import Table from 'react-bootstrap/Table';
import CommentCard from "../../components/cards/CommentCard";
import HorizontalRule from "../../components/layout/HorizontalRule";
import SeriesCard from "../../components/cards/SeriesCard";
import UserListCard from "../../components/cards/UsersListCard";

function MovieDetails() {
  const details = [{ field: "Name", value: "Avatar" }, { field: "Director", value: "James Cameron" }];
  const awards = [{ name: "Academy Award for Best Cinematography", year: "2010" }, { name: "Golden Globe Award for Best Motion Picture – Drama", year: "2010" }];
  const series = [{ name: "Avatar", nr: 1 }, { name: "Avatar: Way of Water", nr: 2 }];
  const comments = [{ text: "Amazing", author: "Catarina" }, { text: "BEST MOVIE EVER!", author: "John Doe" }];
  const users = [{ name: "Catarina" }, { name: "Henrique" }, { name: "Patricia" }, { name: "John Doe" }];

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
                  {details.map(d => <tr><td>{d.field}</td><td>{d.value}</td></tr>)}
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
                  {awards.map(a => <tr><td>{a.name}</td><td>{a.year}</td></tr>)}
                </tbody>
              </Table>
            </div>
          </Col>
        </Row>

        <HorizontalRule text={"Social"} />

        <Row className="mt-5 pb-2 pt-4 pb-4">
          <Col className="d-flex flex-column align-items-center bg-light rounded-2" style={{ marginRight: "10px" }}>
            <h4 className="text-center">People that watched</h4>
            <UserListCard users={users} />
          </Col>
          <Col className="bg-light rounded-2">
            <h4 className="text-center">Comments</h4>
            <div className="d-flex flex-column bd-highlight mb-3">
              {comments.map(c => <CommentCard comment={c} />)}
            </div>
          </Col>
        </Row>

        <HorizontalRule text={"Series"} />

        <Row className="mt-5 pb-2">
          <Col>
            <div className="d-flex flex-row bd-highlight mb-3">
              {series.map(s => <SeriesCard film={s} />)}
            </div>
          </Col>
        </Row>

      </Container>
    </div>
  );
}

export default MovieDetails;
