import React, { useState } from "react";
import InputGroup from "react-bootstrap/InputGroup";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import FriendCard from "../../cards/FriendCard";

function FriendsList({ name, friends }) {
  const [list, setList] = useState(friends);
  const [level, setLevel] = useState(1);

  const showMore = (event) => {
    event.preventDefault();
    setLevel(level + 1);
    setList(
      list.concat(
        list.map((l) => {
          const f = { ...l };
          f.level = level + 1;
          return f;
        })
      )
    );
    // TODO: change to request next level friends from API
  };

  return (
    <div>
      <h4 className="mb-3">{name}</h4>

      <Form className="mb-4">
        <InputGroup className="mb-3">
          <Form.Control
            aria-label="Add new Friend by Username"
            placeholder="Username"
          />
          <Button type="submit" variant="darkblue">
            Add Friend
          </Button>
        </InputGroup>
      </Form>

      {list && Array.isArray(list) && list.length > 0 ? (
        <div>
          {list.map((friend, index) => (
            <FriendCard friend={friend} key={index} showLevel={true} withIcon={true}/>
          ))}
        </div>
      ) : (
        <p>
          <i>No friends to list</i>
        </p>
      )}

      <p className="text-start">
        <button
          type="button"
          className="btn btn-link"
          onClick={(event) => showMore(event)}
        >
          Show More...
        </button>
      </p>
    </div>
  );
}

export default FriendsList;
