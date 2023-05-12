import React, { useState } from "react";
import InputGroup from "react-bootstrap/InputGroup";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import FriendCard from "../../cards/FriendCard";

import UsersAPI from "../../../api/UsersAPI";

function FriendsList({ username, name, friends }) {
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

  const [friendName, setFriendName] = useState("");

  const handleAddFriend = (event, friend) => {
    event.preventDefault();

    const api = new UsersAPI(username);
    api.addFriend(
      friend,
      (response) => {
        if (response) {
          console.log("Friend added successfully");
          // TODO: get friends list again
          // OR add to list and make this order by level when rendering
        } else {
          console.log("Friend not exists or already added");
        }
      },
      (error) => {
        console.log(error);
      }
    );
  };

  const handleRemoveFriend = (event, friend) => {
    event.preventDefault();

    const api = new UsersAPI(username);
    api.removeFriend(
      friend,
      (response) => {
        if (response) {
          console.log("Friend removed successfully");
          // TODO: remove from list
        } else {
          console.log("Friend not of the user");
        }
      },
      (error) => {
        console.log(error);
      }
    );
  };

  return (
    <div>
      <h4 className="mb-3">{name}</h4>

      <Form
        className="mb-4"
        onSubmit={(event) => handleAddFriend(event, friendName)}
      >
        <InputGroup className="mb-3">
          <Form.Control
            aria-label="Add new Friend by Username"
            placeholder="Username"
            value={friendName}
            onChange={(event) => setFriendName(event.target.value)}
          />
          <Button type="submit" variant="darkblue">
            Add Friend
          </Button>
        </InputGroup>
      </Form>

      {list && Array.isArray(list) && list.length > 0 ? (
        <div>
          {list.map((friend, index) => (
            <FriendCard
              friend={friend}
              key={index}
              showLevel={true}
              withIcon={true}
              handleAddFriend={handleAddFriend}
              handleRemoveFriend={handleRemoveFriend}
            />
          ))}

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
      ) : (
        <p>
          <i>No friends to list</i>
        </p>
      )}
    </div>
  );
}

export default FriendsList;
