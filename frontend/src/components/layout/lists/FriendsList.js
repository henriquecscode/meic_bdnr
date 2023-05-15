import React, { useState } from "react";
import InputGroup from "react-bootstrap/InputGroup";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import FriendCard from "../../cards/FriendCard";

import UsersAPI from "../../../api/UsersAPI";
import RecommendationsAPI from "../../../api/RecommendationsAPI";

function FriendsList({ username, name, friends }) {
  const [list, setList] = useState(friends);
  const [level, setLevel] = useState(1);

  const [friendName, setFriendName] = useState("");

  const showMore = (event) => {
    event.preventDefault();

    const api = new RecommendationsAPI(username);
    api.getFriendsFilms(
      level + 1,
      (data) => {
        if (data && Array.isArray(data) && data.length > 0) {
          const more = data.map((element, index) => {
            return {
              id: index,
              username: element.user.username,
              picture: "user.png",
              level: level + 1,
            };
          });

          // remove from list all friends that are already in the new list
          const cleanList = list.filter(
            (f) => more.find((m) => m.username === f.username) === undefined
          );

          setList(cleanList.concat(more));
          setLevel(level + 1);
        } else {
          setLevel(-1);
        }
      },
      (error) => {
        console.log(error);
      }
    );
  };

  const handleAddFriend = (event, friend) => {
    event.preventDefault();

    const api = new UsersAPI(username);
    api.addFriend(
      friend,
      (response) => {
        if (response) {
          console.log("Friend added successfully");

          if (list.find((f) => f.username === friend) !== undefined) {
            list.find((f) => f.username === friend).level = 1;
          } else {
            list.unshift({
              id: list.length,
              username: friend,
              picture: "user.png",
              level: 1,
            });
          }
          setFriendName("");
          setList([...list]);
        } else {
          console.log("Friend not exists or already added");
        }
      },
      (error) => {
        console.log(error);
        alert("Friend not exists or already added");
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
          list.find((f) => f.username === friend).level = -1;
          setList([...list]);
        } else {
          console.log("Friend not of the user");
        }
      },
      (error) => {
        console.log(error);
        alert("Friend not of the user");
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
            {level > 0 ? (
              <button
                type="button"
                className="btn btn-link"
                onClick={(event) => showMore(event)}
              >
                Show More...
              </button>
            ) : (
              <i>No more friends to show</i>
            )}
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
