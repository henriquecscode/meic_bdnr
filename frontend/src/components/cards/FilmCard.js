import Card from "react-bootstrap/Card";

export default function FilmCard({ film, username, index }) {
  return (
    <div className="p-2 bd-highlight" key={index}>
      <Card>
        <Card.Body>
          <Card.Title>{film.name}</Card.Title>
          {film.startYear ? (
            <Card.Subtitle className="mb-2 text-muted">
              Year: {film.startYear}
            </Card.Subtitle>
          ) : null}

          {film.nr ? (
            <Card.Subtitle className="mb-2 text-muted">
              Series number: {film.nr}
            </Card.Subtitle>
          ) : null}
          <Card.Text></Card.Text>
          <Card.Link href={`/movies/${film.tid}?username=${username}`}>
            See details
          </Card.Link>
        </Card.Body>
      </Card>
    </div>
  );
}
