import Badge from "react-bootstrap/Badge";
import Card from "react-bootstrap/Card";
import ListGroup from "react-bootstrap/ListGroup";
import Button from "react-bootstrap/Button";
import OverlayTrigger from "react-bootstrap/OverlayTrigger";
import Popover from "react-bootstrap/Popover";
import { FaInfo } from "react-icons/fa";

export default function ListCard({ list }) {
  const popover = (name, info) => (
    <Popover id="popover-basic">
      <Popover.Header as="h3">{name}</Popover.Header>
      <Popover.Body>{info}</Popover.Body>
    </Popover>
  );

  const getInfoButton = (name, info) => {
    return info ? (
      <OverlayTrigger
        trigger="click"
        placement="right"
        overlay={popover(name, info)}
        rootClose
      >
        <Button className="rounded-circle mx-2" size="sm">
          <FaInfo size={12} />
        </Button>
      </OverlayTrigger>
    ) : null;
  };

  const getNid = (element) => {
    if (element.nid)
      return (
        <Card.Link href={`https://www.imdb.com/name/${element.nid}`}>
          {element.name}
        </Card.Link>
      );
    else return element.name;
  };

  return (
    <div>
      {list && Array.isArray(list) && list.length > 0 ? (
        <ListGroup variant="flush" className="mb-3">
          {list.map((e, index) => (
            <ListGroup.Item key={index}>
              {getInfoButton(e.name, e.info)}

              <Badge bg="primary" className="mx-2" pill>
                {e.awards}
              </Badge>

              {getNid(e)}
            </ListGroup.Item>
          ))}
        </ListGroup>
      ) : (
        <p>
          <i>No items to list</i>
        </p>
      )}
    </div>
  );
}
