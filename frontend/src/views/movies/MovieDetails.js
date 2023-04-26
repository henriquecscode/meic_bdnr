import React from "react";
import Table from 'react-bootstrap/Table';
import Card from 'react-bootstrap/Card';
import ListGroup from 'react-bootstrap/ListGroup';

function MovieDetails() {
  const series = [{ name: "Avatar", nr: 1 }, { name: "Avatar: Way of Water", nr: 2 }]
  const comments = [{ comment: "Amazing", author: "Catarina" }, { comment: "BEST MOVIE EVER!", author: "John Doe" }]
  const users = [{ name: "Catarina" }, { name: "Henrique" }, { name: "Patricia" }, { name: "John Doe" }]

  const getSeriesCard = (film) => {
    return <div className="p-2 bd-highlight"><Card style={{ width: '18rem' }}>
      <Card.Body>
        <Card.Title>{film.name}</Card.Title>
        <Card.Subtitle className="mb-2 text-muted">Series number: {film.nr}</Card.Subtitle>
        <Card.Text>
        </Card.Text>
        <Card.Link href="#">Card Link</Card.Link>
      </Card.Body>
    </Card> </div>;
  }

  const getCommentCard = (comment, author) => {
    return <div className="p-2 bd-highlight">
      <Card>
        <Card.Body>
          <blockquote className="blockquote mb-0">
            <p>
              {' '}
              {comment} {' '}
            </p>
            <footer className="blockquote-footer">
              <cite title="Source Title">{author}</cite>
            </footer>
          </blockquote>
        </Card.Body>
      </Card>
    </div>;
  }

  const getUserCard = (author) => {
    return <ListGroup.Item>{author}</ListGroup.Item>;
  }

  return (
    <div>
      <h1 className="text-center m-5">Avatar</h1>

      <div className="container">
        <div className="row mt-5 pb-2">
          <div className="col">
            Image
          </div>
          <div className="col">
            <div>
              <h5 className="text-center">Details</h5>
              <Table striped bordered hover size="sm">
                <tbody>
                  <tr>
                    <td>Name</td>
                    <td>Avatar</td>
                  </tr>
                  <tr>
                    <td>Director</td>
                    <td>James Cameron</td>
                  </tr>
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
                  <tr>
                    <td>Academy Award for Best Cinematography </td>
                    <td>2010</td>
                  </tr>
                  <tr>
                    <td>Golden Globe Award for Best Motion Picture – Drama </td>
                    <td>2010</td>
                  </tr>
                </tbody>
              </Table>
            </div>
          </div>
        </div>

        <div className="row mt-5 pb-2">
          <div className="col">
            <h4 className="text-center">People that watched</h4>
            <Card style={{ width: '18rem' }}>
              <ListGroup variant="flush">
                {users.map(u => getUserCard(u.name))}
              </ListGroup>
            </Card>
          </div>
          <div className="col">
            <h4 className="text-center">Comments</h4>
            <div className="d-flex flex-column bd-highlight mb-3">
              {comments.map(c => getCommentCard(c.comment, c.author))}
            </div>
          </div>
        </div>

        <div className="row mt-5 pb-2">
          <div className="col">
            <h4 className="text-center">Series</h4>
            <div className="d-flex flex-row bd-highlight mb-3">
              {series.map(s => getSeriesCard(s))}
            </div>
          </div>
        </div>

      </div>
    </div>
  );
}

export default MovieDetails;
