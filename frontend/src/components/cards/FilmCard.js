import Card from "react-bootstrap/Card";

export default function FilmCard({ film, username, index }) {
  return (
    <div className="p-2 bd-highlight" key={index}>
      <Card style={{ width: "18rem" }}>
        <Card.Body>
          <Card.Title>{film.name}</Card.Title>
          {film.nr ? (
            <Card.Subtitle className="mb-2 text-muted">
              Series number: {film.nr}
            </Card.Subtitle>
          ) : null}
          <Card.Text></Card.Text>
          <Card.Link href={`/movies/${film.id}?username=${username}`}>
            See details
          </Card.Link>
        </Card.Body>
      </Card>
    </div>
  );
}
