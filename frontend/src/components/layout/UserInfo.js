import React from "react";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";

function UserInfo({ user }) {
  return (
    <Row>
      <Col sm={3}>
        <img className="profile-img" src={user.picture} alt="avatar" />
      </Col>
      <Col sm={9}>
        <h2 className="mt-4 mb-1">{user.username}</h2>
        <p className="mb-4 text-muted">{user.email}</p>

        <p className="mb-1">{user.bio}</p>
        <p>
          <b>Nationality: </b>
          {user.nationality}
        </p>
      </Col>
    </Row>
  );
}

export default UserInfo;
