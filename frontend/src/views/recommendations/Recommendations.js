import React, { useState, useEffect, useRef } from "react";
import Container from "react-bootstrap/Container";
import Tabs from "react-bootstrap/Tabs";
import Tab from "react-bootstrap/Tab";
import Form from "react-bootstrap/Form";
import InputGroup from "react-bootstrap/InputGroup";
import Button from "react-bootstrap/Button";
import MoviesList from "../../components/layout/lists/MoviesList";

import RecommendationsAPI from "../../api/RecommendationsAPI";
import UsersAPI from "../../api/UsersAPI";

function Recommendations({ username }) {
  const [moviesFriends, setMoviesFriends] = useState([]);
  const [moviesCountry, setMoviesCountry] = useState([]);
  const [moviesAdvice, setMoviesAdvice] = useState([]);
  const [userWatchlist, setUserWatchlist] = useState([]);

  const [loadingFriends, setLoadingFriends] = useState(true);
  const [loadingCountry, setLoadingCountry] = useState(true);
  const [loadingAdvice, setLoadingAdvice] = useState(true);
  const [loadingWatchlist, setLoadingWatchlist] = useState(true);

  const formControl = useRef();
  const friendsLevel = 3;

  const getFriendsFilms = (api, level) => {
    setLoadingFriends(true);

    api.getFriendsFilms(
      level,
      (data) => {
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

    // Watchlist
    const usersAPI = new UsersAPI(username);
    usersAPI.getProfile(
      (data) => {
        setUserWatchlist(
          data.toWatch && Array.isArray(data.toWatch) && data.toWatch.length > 0
            ? data.toWatch.map((movie) => movie.tid)
            : []
        );
        setLoadingWatchlist(false);
      },
      (error) => {
        setUserWatchlist([]);
        setLoadingWatchlist(false);
        console.log(error);
      }
    );
  }, [friendsLevel, username]);

  const handleFriendshipClick = () => {
    const api = new RecommendationsAPI(username);
    getFriendsFilms(api, formControl.current.value);
  };

  const handleAddWatchlistClick = (movie) => {
    const usersAPI = new UsersAPI(username);
    usersAPI.addWatchlist(
      movie.tid,
      (data) => {
        if (data) {
          console.log("Added to watchlist successfully!");
          setUserWatchlist([...userWatchlist, movie.tid]);
        }
      },
      (error) => {
        console.log(error);
        alert("Error adding to watchlist!");
      }
    );
  };

  const handleRemoveWatchlistClick = (movie) => {
    const usersAPI = new UsersAPI(username);
    usersAPI.removeWatchlist(
      movie.tid,
      (data) => {
        if (data) {
          console.log("Removed from watchlist successfully!");
          setUserWatchlist(userWatchlist.filter((tid) => tid !== movie.tid));
        }
      },
      (error) => {
        console.log(error);
        alert("Error removing from watchlist!");
      }
    );
  };

  const getMoviesList = (movies, loading) => {
    return (
      <MoviesList
        username={username}
        movies={movies}
        watchlist={userWatchlist}
        loading={loading || loadingWatchlist}
        onAddWatchlistClick={handleAddWatchlistClick}
        onRemoveWatchlistClick={handleRemoveWatchlistClick}
      />
    );
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

          {getMoviesList(moviesFriends, loadingFriends)}
        </Tab>
        <Tab eventKey="country" title="Friends Same Country">
          <p className="fw-bold">
            List of Movies that the Friends of your Country Watched
          </p>

          {getMoviesList(moviesCountry, loadingCountry)}
        </Tab>
        <Tab eventKey="advice" title="Friends WatchList">
          <p className="fw-bold">
            List of Movies that your Friends want to Watch that you didn't like
          </p>

          {getMoviesList(moviesAdvice, loadingAdvice)}
        </Tab>
      </Tabs>
    </Container>
  );
}

export default Recommendations;
