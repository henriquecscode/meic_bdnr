import React from "react";
import Col from "react-bootstrap/Col";
import Image from "react-bootstrap/Image";
import Row from "react-bootstrap/Row";
import getImageSrc from "../../utils/utils";

function UserInfo({ user }) {
  if (!user) {
    return null;
  }

  return (
    <Row>
      <Col sm={3}>
        <Image
          src={getImageSrc(user.picture)}
          height={200}
          width={200}
          className="thumbnail-image profile-img"
          alt="avatar"
        />
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
