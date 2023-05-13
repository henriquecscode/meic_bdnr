import React, { useState, useEffect } from "react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import UserInfo from "../../components/layout/UserInfo";
import FriendsList from "../../components/layout/lists/FriendsList";
import WatchList from "../../components/layout/lists/WatchList";
import InteractionsList from "../../components/layout/lists/InteractionsList";
import SeriesList from "../../components/layout/lists/SeriesList";

import UsersAPI from "../../api/UsersAPI";

function Profile({ username }) {
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

  const [user, setUser] = useState({});
  const [friends, setFriends] = useState([]);
  const [movies, setMovies] = useState([]);
  const [interactions, setInteractions] = useState([]);
  // const [series, setSeries] = useState([]);

  const [loading, setLoading] = useState(true);

  useEffect(() => {
    document.title = `FilmFriend - ${username}'s Profile`;

    const api = new UsersAPI(username);
    api.getProfile(
      (data) => {
        setUser({
          ...data.user,
          email: data.user.username + "@gmail.com",
          bio: "Hi! I'm " + data.user.name + " and I love movies!",
          nationality: data.countries
            ? data.countries.map((c) => c.name).join(", ")
            : "Unknown",
        });

        setFriends(
          data.friends
            ? data.friends.map((f, i) => {
                return {
                  id: i,
                  username: f.username,
                  picture: "user.png", // TODO: change to f.picture when available
                  level: 1,
                };
              })
            : []
        );

        setMovies(data.toWatch);

        setInteractions(
          data.films.map((film, index) => {
            return {
              id: index,
              movie: film.title,
              ...film.watched,
            };
          })
        );

        setLoading(false);
      },
      (error) => {
        setUser({});
        setFriends([]);
        setMovies([]);
        setInteractions([]);
        setLoading(false);
        console.log(error);
      }
    );
  }, [username]);

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
              {loading ? (
                <p>
                  <i>Loading FriendsList...</i>
                </p>
              ) : (
                <FriendsList
                  username={username}
                  name={"Friends List"}
                  friends={friends}
                />
              )}
            </Col>
            <Col sm={9} className="ps-4">
              <WatchList
                username={username}
                name={"WatchList"}
                movies={movies}
                setMovies={setMovies}
              />
              <br />
              <InteractionsList
                username={username}
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
