import React from "react";
import Container from "react-bootstrap/Container";
import Tabs from "react-bootstrap/Tabs";
import Tab from "react-bootstrap/Tab";
import Form from "react-bootstrap/Form";
import InputGroup from "react-bootstrap/InputGroup";
import Button from "react-bootstrap/Button";
import MoviesList from "../../components/layout/lists/MoviesList";

function Recommendations({ username }) {
  // TODO: change to request movies from API - a movies list per tab

  const moviesFriends = [
    {
      id: 1,
      image: "movie1.jpg",
      title: "Movie 1",
      genre: "Action",
      description:
        "Lorem ipsum dolor sit amet consectetur adipisicing elit. Quisquam, voluptatum.",
      watchlist: true,
      watched: false,
    },
    {
      id: 2,
      image: "movie2.jpg",
      title: "Movie 2",
      genre: "Action",
      description:
        "Lorem ipsum dolor sit amet consectetur adipisicing elit. Quisquam, voluptatum.",
      watchlist: false,
      watched: true,
    },
  ];

  return (
    <Container className="py-5">
      <Tabs
        defaultActiveKey="friends"
        id="recommendations-tab"
        className="mb-3"
      >
        <Tab eventKey="friends" title="Friends Liked">
          <p className="fw-bold">
            List of Movies that your Friends Liked that you haven't watched
          </p>
          <Form className="mb-4">
            <InputGroup className="mb-3">
              <Form.Control
                aria-label="Level of Friendship to consider"
                placeholder="Level of Friendship"
              />
              <Button type="submit" variant="darkblue">
                Friendship
              </Button>
            </InputGroup>
          </Form>

          <MoviesList username={username} movies={moviesFriends} />
        </Tab>
        <Tab eventKey="country" title="Friends Same Country">
          <p className="fw-bold">
            List of Movies that the Friends of your Country Watched
          </p>
        </Tab>
        <Tab eventKey="advice" title="Friends WatchList">
          <p className="fw-bold">
            List of Movies that your Friends want to Watch that you didn't like
          </p>
        </Tab>
      </Tabs>
    </Container>
  );
}

export default Recommendations;
