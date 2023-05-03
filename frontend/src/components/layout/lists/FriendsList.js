import React, { useState } from "react";
import InputGroup from "react-bootstrap/InputGroup";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";

function FriendsList({ name, friends }) {
  return (
    <div>
      <h4 className="mb-3">{name}</h4>

      {friends && Array.isArray(friends) && friends.length > 0 ? (
        <div className="mb-3">
          {friends.map((friend) => (
            <div key={friend.id} className="d-flex">
              <img
                src={friend.picture}
                alt={friend.username}
                className="thumbnail-image me-3"
                height={50}
                width={50}
              />
              <div>
                <p className="mb-1">{friend.username}</p>
                <p className="text-muted">{`Friend ${friend.level}º level`}</p>
              </div>
            </div>
          ))}
        </div>
      ) : (
        <p>
          <i>No friends to list</i>
        </p>
      )}

      <Form>
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
    </div>
  );
}

export default FriendsList;
