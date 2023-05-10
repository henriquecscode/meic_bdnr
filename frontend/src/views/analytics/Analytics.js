import React, { useEffect, useState } from "react";
import Nav from "react-bootstrap/Nav";
import ListCard from "../../components/cards/ListCard";
import AnalyticsAPI from "../../api/AnalyticsAPI";

function Analytics({ username }) {
  const [tab, setTab] = useState("friends");

  const [friendsWatchedSeries, setFriendsWatchedSeries] = useState([]);
  const [workersCountry, setWorkersCountry] = useState([]);
  const [awardsGenre, setAwardsGenre] = useState([]);
  const [awardsWorkers, setAwardsWorkers] = useState([]);
  const [awardsCountries, setAwardsCountries] = useState([]);

  useEffect(() => {
    const api = new AnalyticsAPI();

    // Friends
    api.getFriendsWatchedSeries(username,
      (json) => {
        setFriendsWatchedSeries(json.map((item) => { return { "name": item.genre.name, "awards": item.awards, "info": item  } }));
      },
      (error) => {
        setFriendsWatchedSeries([]);
        console.log(error);
      }
    );

    // Cast
    api.getWorkersCountry(
      (json) => {
        setWorkersCountry(json.map((item) => { return { "name": item.worker.name, "nid": item.worker.nid, "info": "Country: " + JSON.stringify(item.country.name) + ".\n Work: " + JSON.stringify(item.titles.map((t) => t.name)) } }));
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
        setAwardsGenre(json.map((item) => { return { "name": item.genre.name, "awards": item.awards } }));
      },
      (error) => {
        setAwardsGenre([]);
        console.log(error);
      }
    );

    api.getWorkersAwards(
      5,
      (json) => {
        setAwardsWorkers(json.map((item) => { return { "name": item.worker.name, "awards": item.awards, "nid": item.worker.nid } }));
      },
      (error) => {
        setAwardsWorkers([]);
        console.log(error);
      }
    );

    api.getCountryAwards(
      3,
      (json) => {
        setAwardsCountries(json.map((item) => { return { "name": item.country.name, "awards": item.awards } }));
      },
      (error) => {
        setAwardsCountries([]);
        console.log(error);
      }
    );
  }, []);

  const getFriendsTab = () => {
    return (
      <>
        <h5 className="text-center">
          Friends that fully watched a common film series
        </h5>
        <div className="my-4 d-flex justify-content-center">
          <ListCard list={friendsWatchedSeries} />
        </div>
      </>
    );
  };

  const getCastTab = () => {
    return (
      <>
        <h5 className="text-center">Cast that worked in their home country</h5>
        <div className="my-4 d-flex justify-content-center">
          <ListCard list={workersCountry} />
        </div>
      </>
    );
  };

  const getAwardsTab = () => {
    return (
      <>
        <h5 className="text-center">TOP 5 most awarded genres</h5>
        <div className="my-4 d-flex justify-content-center">
          <ListCard list={awardsGenre} />
        </div>
        <h5 className="text-center">TOP 5 most awarded workers</h5>
        <div className="my-4 d-flex justify-content-center">
          <ListCard list={awardsWorkers} />
        </div>
        <h5 className="text-center">
          TOP 3 countries with the most awarded workers
        </h5>
        <div className="my-4 d-flex justify-content-center">
          <ListCard list={awardsCountries} />
        </div>
      </>
    );
  };

  const displayTab = () => {
    switch (tab) {
      case "friends":
        return getFriendsTab();
      case "cast":
        return getCastTab();
      case "awards":
        return getAwardsTab();
      default:
        return <></>;
    }
  };

  return (
    <div>
      <h1 className="text-center m-5">Analytics</h1>

      <Nav
        justify
        variant="tabs"
        className="mb-3"
        activeKey={tab}
        onSelect={(k) => setTab(k)}
      >
        <Nav.Item>
          <Nav.Link className="fs-4" eventKey="friends">
            Friends
          </Nav.Link>
        </Nav.Item>
        <Nav.Item>
          <Nav.Link className="fs-4" eventKey="cast">
            Cast
          </Nav.Link>
        </Nav.Item>
        <Nav.Item>
          <Nav.Link className="fs-4" eventKey="awards">
            Awards
          </Nav.Link>
        </Nav.Item>
      </Nav>

      {displayTab()}
    </div>
  );
}

export default Analytics;
