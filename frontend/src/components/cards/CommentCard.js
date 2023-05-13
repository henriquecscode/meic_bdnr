import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";

export default function CommentCard({ comment, index, withDelete = false, onDelete = null }) {
  return (
    <div className="py-1 bd-highlight" key={index}>
      <Card>
        <Card.Body className="p-2">
          <Card.Text className="d-inline">
            {comment.text}{" "}
            <span className="blockquote-footer">
              <cite title="Source Title">{comment.author.name}</cite>
            </span>
          </Card.Text>
        </Card.Body>
        {withDelete ? <Button className="me-2 mb-2 align-self-end" size="sm" variant="danger" onClick={onDelete}>Delete</Button> : null}
      </Card>
    </div>
  );
}
