import Card from 'react-bootstrap/Card';
import ListGroup from 'react-bootstrap/ListGroup';

export default function UserListCard({ users }) {
  return <Card className="w-50 text-center">
          <ListGroup variant="flush">
            {users.map(u => <ListGroup.Item>{u.name}</ListGroup.Item>)}
          </ListGroup>
        </Card>;
}