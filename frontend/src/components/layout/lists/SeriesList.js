import React from "react";
import Card from "react-bootstrap/Card";

function SeriesList({ name, series }) {
  return (
    <div>
      <h4 className="mb-3">{name}</h4>

      {series && Array.isArray(series) && series.length > 0 ? (
        <div className="cards-container mb-3">
          {series.map((serie) => (
            <Card key={serie.id} className="card-item">
              <Card.Img variant="top" src={serie.image} alt={serie.title} />
              <Card.Body>
                <Card.Title>{serie.title}</Card.Title>
                <Card.Subtitle className="mb-2 text-muted">{`Nº Movies: ${serie.n_movies}`}</Card.Subtitle>
                <Card.Text>Some Description...</Card.Text>
              </Card.Body>
            </Card>
          ))}
        </div>
      ) : (
        <p>
          <i>No series to list</i>
        </p>
      )}
    </div>
  );
}

export default SeriesList;
