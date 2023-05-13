import Card from "react-bootstrap/Card";

export default function SeriesCard({ comment, index }) {
  return (
    <div className="py-1 bd-highlight" key={index}>
      <Card>
        <Card.Body className="p-2">
          <Card.Text className="d-inline">
            {comment.text}{" "}
            <span className="blockquote-footer">
              <cite title="Source Title">{comment.author}</cite>
            </span>
          </Card.Text>
        </Card.Body>
      </Card>
    </div>
  );
}
