import Card from 'react-bootstrap/Card';

export default function SeriesCard({comment}) {
  return <div className="p-2 bd-highlight">
          <Card>
            <Card.Body>
              <blockquote className="blockquote d-inline mb-0">
                <p>
                  {' '} {comment.text} {' '} 
                  <span className="blockquote-footer">
                    <cite title="Source Title">{comment.author}</cite>
                  </span>
                </p>
              </blockquote>
            </Card.Body>
          </Card>
        </div>;
}