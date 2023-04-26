import Card from 'react-bootstrap/Card';

export default function SeriesCard({film}) {
  return <div className="p-2 bd-highlight">
          <Card style={{ width: '18rem' }}>
            <Card.Body>
              <Card.Title>{film.name}</Card.Title>
              <Card.Subtitle className="mb-2 text-muted">Series number: {film.nr}</Card.Subtitle>
              <Card.Text>
              </Card.Text>
              <Card.Link href="#">Card Link</Card.Link>
            </Card.Body>
          </Card>
        </div>;
}