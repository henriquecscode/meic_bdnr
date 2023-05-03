import Card from 'react-bootstrap/Card';
import ListGroup from 'react-bootstrap/ListGroup';

export default function ListCard({ list }) {
  return <Card className="w-75 text-center">
          <ListGroup variant="flush">
            {list.map((e, index) => <ListGroup.Item key={index}>{e.name}</ListGroup.Item>)}
          </ListGroup>
        </Card>;
}