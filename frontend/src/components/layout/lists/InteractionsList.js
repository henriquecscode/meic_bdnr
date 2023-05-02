import React from "react";
import Card from "react-bootstrap/Card";

function InteractionsList({ name, interactions }) {
  return (
    <div>
      <h4 className="mb-3">{name}</h4>

      {interactions &&
      Array.isArray(interactions) &&
      interactions.length > 0 ? (
        <div className="mb-3" style={{ display: "grid", gap: "0.75rem" }}>
          {interactions.map((interaction) => (
            <Card key={interaction.id} className="card-item w-100">
              <Card.Body>
                <Card.Title>{interaction.movie.title}</Card.Title>
                <Card.Subtitle className="mb-2 text-muted">{`Vote: ${interaction.vote}`}</Card.Subtitle>
                <Card.Text>{interaction.comment}</Card.Text>
              </Card.Body>
            </Card>
          ))}
        </div>
      ) : (
        <p>
          <i>No interactions to list</i>
        </p>
      )}
    </div>
  );
}

export default InteractionsList;
