import React from "react";
import Card from "react-bootstrap/Card";

function InteractionsList({ username, name, interactions }) {
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
                <Card.Title>
                  <Card.Link
                    href={`/movies/${interaction.movie.tid}?username=${username}`}
                    target="_blank"
                    style={{ color: "inherit", textDecoration: "inherit" }}
                  >
                    {interaction.movie.name}
                  </Card.Link>
                </Card.Title>

                {interaction.vote && interaction.vote !== "" && (
                  <Card.Subtitle className="mb-2 text-muted">{`Vote: ${interaction.vote}`}</Card.Subtitle>
                )}

                {interaction.comment && interaction.comment !== "" && (
                  <Card.Text className="line-ellipsis">
                    {interaction.comment}
                  </Card.Text>
                )}

                <span className="blockquote-footer ps-2">
                  <cite title="date">{interaction.date.split("T")[0]}</cite>
                </span>
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
