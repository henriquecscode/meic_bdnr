import React, { useState, useEffect, useRef } from "react";
import Container from "react-bootstrap/Container";
import Tabs from "react-bootstrap/Tabs";
import Tab from "react-bootstrap/Tab";
import Form from "react-bootstrap/Form";
import InputGroup from "react-bootstrap/InputGroup";
import Button from "react-bootstrap/Button";
import MoviesList from "../../components/layout/lists/MoviesList";

import RecommendationsAPI from "../../api/RecommendationsAPI";

function Recommendations({ username }) {
  // TODO: check with expected data format
  // const moviesFriendsss = [
  //   {
  //     id: 1,
  //     image: "movie1.jpg",
  //     title: "Movie 1",
  //     genre: "Action",
  //     description:
  //       "Lorem ipsum dolor sit amet consectetur adipisicing elit. Quisquam, voluptatum.",
  //     watchlist: true,
  //     watched: false,
  //   },
  //   {
  //     id: 2,
  //     image: "movie2.jpg",
  //     title: "Movie 2",
  //     genre: "Action",
  //     description:
  //       "Lorem ipsum dolor sit amet consectetur adipisicing elit. Quisquam, voluptatum.",
  //     watchlist: false,
  //     watched: true,
  //   },
  // ];

  const [moviesFriends, setMoviesFriends] = useState([]);
  const [moviesCountry, setMoviesCountry] = useState([]);
  const [moviesAdvice, setMoviesAdvice] = useState([]);

  const [loadingFriends, setLoadingFriends] = useState(true);
  const [loadingCountry, setLoadingCountry] = useState(true);
  const [loadingAdvice, setLoadingAdvice] = useState(true);

  const formControl = useRef();
  const friendsLevel = 3;

  const getFriendsFilms = (api, level) => {
    setLoadingFriends(true);

    api.getFriendsFilms(
      level,
      (data) => {
        console.log("friends", data);
        setMoviesFriends(
          data && Array.isArray(data) && data.length > 0 ? data : []
        );
        setLoadingFriends(false);
      },
      (error) => {
        setMoviesFriends([]);
        setLoadingFriends(false);
        console.log(error);
      }
    );
  };

  useEffect(() => {
    document.title = "FilmFriends - Recommend Me";

    const api = new RecommendationsAPI(username);

    // Friends
    getFriendsFilms(api, friendsLevel);

    // Country
    api.getCountryFilms(
      (data) => {
        console.log("country", data);
        setMoviesCountry(
          data && Array.isArray(data) && data.length > 0 ? data : []
        );
        setLoadingCountry(false);
      },
      (error) => {
        setMoviesCountry([]);
        setLoadingCountry(false);
        console.log(error);
      }
    );

    // Advice
    api.getAdviceFilms(
      (data) => {
        console.log("advise", data);
        setMoviesAdvice(
          data && Array.isArray(data) && data.length > 0 ? data : []
        );
        setLoadingAdvice(false);
      },
      (error) => {
        setMoviesAdvice([]);
        setLoadingAdvice(false);
        console.log(error);
      }
    );
  }, [friendsLevel, username]);

  const handleFriendshipClick = () => {
    const api = new RecommendationsAPI(username);
    getFriendsFilms(api, formControl.current.value);
  };

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
            (1-3)
          </p>

          <InputGroup className="mb-4">
            <Form.Control
              aria-label="Level of Friendship to consider"
              placeholder="Level of Friendship"
              defaultValue={friendsLevel}
              type="number"
              min={1}
              max={3}
              ref={formControl}
            />
            <Button variant="darkblue" onClick={handleFriendshipClick}>
              Friendship
            </Button>
          </InputGroup>

          <MoviesList
            username={username}
            movies={moviesFriends}
            loading={loadingFriends}
          />
        </Tab>
        <Tab eventKey="country" title="Friends Same Country">
          <p className="fw-bold">
            List of Movies that the Friends of your Country Watched
          </p>
          <MoviesList
            username={username}
            movies={moviesCountry}
            loading={loadingCountry}
          />
        </Tab>
        <Tab eventKey="advice" title="Friends WatchList">
          <p className="fw-bold">
            List of Movies that your Friends want to Watch that you didn't like
          </p>
          <MoviesList
            username={username}
            movies={moviesAdvice}
            loading={loadingAdvice}
          />
        </Tab>
      </Tabs>
    </Container>
  );
}

export default Recommendations;
