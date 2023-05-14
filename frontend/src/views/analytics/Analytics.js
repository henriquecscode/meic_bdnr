import React, { useEffect, useState } from "react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Tabs from "react-bootstrap/Tabs";
import Tab from "react-bootstrap/Tab";
import ListCard from "../../components/cards/ListCard";
import AnalyticsAPI from "../../api/AnalyticsAPI";

function Analytics({ username }) {
  const [friendsWatchedSeries, setFriendsWatchedSeries] = useState([]);
  const [workersCountry, setWorkersCountry] = useState([]);
  const [awardsGenre, setAwardsGenre] = useState([]);
  const [awardsWorkers, setAwardsWorkers] = useState([]);
  const [awardsCountries, setAwardsCountries] = useState([]);

  useEffect(() => {
    document.title = "FilmFriends - Analytics";

    const api = new AnalyticsAPI();

    // Friends
    api.getFriendsWatchedSeries(
      username,
      (json) => {
        if (json && Array.isArray(json) && json.length > 0) {
          setFriendsWatchedSeries(
            json.map((item) => {
              return {
                name:
                  item.series.name +
                  " (" +
                  item.users.map((u) => u.username).join(", ") +
                  ")",
                awards: item.users.length,
              };
            })
          );
        } else {
          setFriendsWatchedSeries([]);
        }
      },
      (error) => {
        setFriendsWatchedSeries([]);
        console.log(error);
      }
    );

    // Cast
    api.getWorkersCountry(
      (json) => {
        if (json && Array.isArray(json) && json.length > 0) {
          setWorkersCountry(
            json.map((item) => {
              return {
                name: item.worker.name,
                nid: item.worker.nid,
                info:
                  "Country: " +
                  JSON.stringify(item.country.name) +
                  ".\n Work: " +
                  JSON.stringify(item.titles.map((t) => t.name)),
              };
            })
          );
        } else {
          setWorkersCountry([]);
        }
      },
      (error) => {
        setWorkersCountry([]);
        console.log(error);
      }
    );

    // Awards
    api.getGenreAwards(
      5,
      (json) => {
        if (json && Array.isArray(json) && json.length > 0) {
          setAwardsGenre(
            json.map((item) => {
              return { name: item.genre.name, awards: item.awards };
            })
          );
        } else {
          setAwardsGenre([]);
        }
      },
      (error) => {
        setAwardsGenre([]);
        console.log(error);
      }
    );

    api.getWorkersAwards(
      5,
      (json) => {
        if (json && Array.isArray(json) && json.length > 0) {
          setAwardsWorkers(
            json.map((item) => {
              return {
                name: item.worker.name,
                awards: item.awards,
                nid: item.worker.nid,
              };
            })
          );
        } else {
          setAwardsWorkers([]);
        }
      },
      (error) => {
        setAwardsWorkers([]);
        console.log(error);
      }
    );

    api.getCountryAwards(
      3,
      (json) => {
        if (json && Array.isArray(json) && json.length > 0) {
          setAwardsCountries(
            json.map((item) => {
              return { name: item.country.name, awards: item.awards };
            })
          );
        } else {
          setAwardsCountries([]);
        }
      },
      (error) => {
        setAwardsCountries([]);
        console.log(error);
      }
    );
  }, [username]);

  const getFriendsTab = () => {
    return (
      <>
        <p className="fw-bold">
          Film series fully watched by your Friends that you also completed
        </p>
        <ListCard list={friendsWatchedSeries} />
      </>
    );
  };

  const getCastTab = () => {
    return (
      <>
        <p className="fw-bold">Cast that worked in their home country</p>
        <ListCard list={workersCountry} />
      </>
    );
  };

  const getAwardsTab = () => {
    return (
      <Row>
        <Col sm={6} md={4}>
          <p className="fw-bold">TOP 5 most awarded genres</p>
          <ListCard list={awardsGenre} />
        </Col>
        <Col sm={6} md={4}>
          <p className="fw-bold">TOP 5 most awarded workers</p>
          <ListCard list={awardsWorkers} />
        </Col>
        <Col sm={6} md={4}>
          <p className="fw-bold">
            TOP 3 countries with the most awarded workers
          </p>
          <ListCard list={awardsCountries} />
        </Col>
      </Row>
    );
  };

  return (
    <Container className="py-5">
      <Tabs id="analytics-tab" className="mb-3" defaultActiveKey="friends">
        <Tab eventKey="friends" title="Friends">
          {getFriendsTab()}
        </Tab>
        <Tab eventKey="cast" title="Cast">
          {getCastTab()}
        </Tab>
        <Tab eventKey="awards" title="Awards">
          {getAwardsTab()}
        </Tab>
      </Tabs>
    </Container>
  );
}

export default Analytics;
