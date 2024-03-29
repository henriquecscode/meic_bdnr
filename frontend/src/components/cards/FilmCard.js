import Card from "react-bootstrap/Card";

export default function FilmCard({
  film,
  username,
  index,
  detailsTopRight = true,
}) {
  return (
    <Card className="my-2 h-100" key={index}>
      <Card.Body>
        <Card.Title className="m-details">{film.name}</Card.Title>
        <Card.Subtitle
          className={"text-muted" + (detailsTopRight ? "" : " mb-2")}
        >
          {film.startYear ? "Year: " + film.startYear : null}
          {film.startYear && film.nr ? " | " : null}
          {film.nr ? "Series number: " + film.nr : null}
        </Card.Subtitle>

        <Card.Link
          href={`/movies/${film.tid}?username=${username}`}
          className={detailsTopRight ? "details-link" : ""}
        >
          See details
        </Card.Link>
      </Card.Body>
    </Card>
  );
}
