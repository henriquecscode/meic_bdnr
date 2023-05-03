import React from "react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import UserInfo from "../../components/layout/UserInfo";
import FriendsList from "../../components/layout/lists/FriendsList";
import WatchList from "../../components/layout/lists/WatchList";
import InteractionsList from "../../components/layout/lists/InteractionsList";
import SeriesList from "../../components/layout/lists/SeriesList";

function Profile({ username }) {
  const user = {
    username: username,
    email: "johndoe@gmail.com",
    bio: "Lorem ipsum dolor sit amet consectetur adipisicing elit. Quisquam, voluptatum.",
    nationality: "American",
    picture: "user.png",
  };

  const friends = [
    {
      id: 1,
      username: "John Doe",
      picture: "user.png",
      level: 1,
    },
    {
      id: 2,
      username: "John Doe",
      picture: "user.png",
      level: 1,
    },
  ];

  const movies = [
    {
      id: 1,
      image: "movie1.jpg",
      title: "Movie 1",
      genre: "Action",
    },
    {
      id: 2,
      image: "movie2.jpg",
      title: "Movie 2",
      genre: "Action",
    },
  ];

  const interactions = [
    {
      id: 1,
      movie: {
        id: 1,
        title: "Movie 1",
      },
      vote: 1,
      comment:
        "Lorem ipsum dolor sit amet consectetur adipisicing elit. Quisquam, voluptatum.",
    },
    {
      id: 2,
      movie: {
        id: 2,
        title: "Movie 2",
      },
      vote: 1,
      comment:
        "Lorem ipsum dolor sit amet consectetur adipisicing elit. Quisquam, voluptatum.",
    },
  ];

  const series = [
    {
      id: 1,
      image: "series1.jpg",
      title: "Series 1",
      n_movies: 10,
    },
    {
      id: 2,
      image: "series2.jpg",
      title: "Series 2",
      n_movies: 10,
    },
  ];

  return (
    <div>
      <div className="profile-info">
        <Container className="py-5">
          <UserInfo user={user} />
        </Container>
      </div>
      <div>
        <Container className="py-5">
          <Row>
            <Col sm={3} className="pe-3 border-end">
              <FriendsList name={"Friends List"} friends={friends} />
            </Col>
            <Col sm={9} className="ps-4">
              <WatchList name={"WatchList"} movies={movies} />
              <br />
              <InteractionsList
                name={"Interactions"}
                interactions={interactions}
              />
              <br />
              <SeriesList name={"Completed Series"} series={series} />
            </Col>
          </Row>
        </Container>
      </div>
    </div>
  );
}

export default Profile;
