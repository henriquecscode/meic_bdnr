import Badge from 'react-bootstrap/Badge';
import Card from 'react-bootstrap/Card';
import ListGroup from 'react-bootstrap/ListGroup';
import Button from 'react-bootstrap/Button';
import OverlayTrigger from 'react-bootstrap/OverlayTrigger';
import Popover from 'react-bootstrap/Popover';
import { FaInfo } from 'react-icons/fa';

export default function ListCard({ list }) {

  const popover = (name, info) => (
    <Popover id="popover-basic">
      <Popover.Header as="h3">{name}</Popover.Header>
      <Popover.Body>
        {info}
      </Popover.Body>
    </Popover>
  );

  const getInfoButton = (name, info) => {
    return (info ? <OverlayTrigger trigger="click" placement="right" overlay={popover(name, info)} rootClose>
      <Button className="rounded-circle mx-2" size="sm"><FaInfo /></Button>
    </OverlayTrigger> : null);
  };

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

          {getInfoButton(e.name, e.info)}

          <Badge bg="primary" className="mx-2" pill>
            {e.awards}
          </Badge>
        </ListGroup.Item>)}
    </ListGroup>
  </Card>;
}