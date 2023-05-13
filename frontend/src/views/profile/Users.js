import React, { useState, useEffect } from "react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import ListCard from "../../components/cards/ListCard";

import UsersAPI from "../../api/UsersAPI";

function Users({ username }) {
  const [loading, setLoading] = useState(true);
  const [users, setUsers] = useState([]);

  useEffect(() => {
    document.title = "FilmFriend - Users";

    const api = new UsersAPI(username);
    api.getAll(
      (data) => {
        console.log(data);

        setUsers(
          data.map((item) => {
            return { name: item.username };
          })
        );
        setLoading(false);
      },
      (error) => {
        setUsers([]);
        setLoading(false);
        console.log(error);
      }
    );
  }, [username]);

  return (
    <Container className="py-5">
      {loading ? (
        <p>
          <i>Loading Users...</i>
        </p>
      ) : (
        <Row>
          <Col>
            <p>
              <b>List of Usernames in the Application</b>
              <br />
              (to test different recommendations according to each user add
              ?username=X in the url)
            </p>
            <ListCard list={users} />
          </Col>
        </Row>
      )}
    </Container>
  );
}

export default Users;
