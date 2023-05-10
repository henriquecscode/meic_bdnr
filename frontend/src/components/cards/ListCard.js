import Badge from 'react-bootstrap/Badge';
import Card from 'react-bootstrap/Card';
import ListGroup from 'react-bootstrap/ListGroup';

export default function ListCard({ list }) {

  const getNid = (element) => {
    if (element.nid)
      return <Card.Link href={`https://www.imdb.com/name/${element.nid}`}>
        {element.name}
      </Card.Link>
    else return element.name;
  }

  return <Card className="w-75 text-center">
    <ListGroup variant="flush">
      {list.map((e, index) =>
        <ListGroup.Item key={index}>
          {getNid(e)}

          <Badge bg="primary" pill>
            {e.awards}
          </Badge>
        </ListGroup.Item>)}
    </ListGroup>
  </Card>;
}