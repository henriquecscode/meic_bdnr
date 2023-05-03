import React, { useState } from "react";
import Nav from 'react-bootstrap/Nav';
import ListCard from "../../components/cards/ListCard";

function Analytics({ username }) {
  const [tab, setTab] = useState('friends');

  const list = [{ name: 'Item 1' }, { name: 'Item 2' }, { name: 'Item 2' }];

  const getFriendsTab = () => {
    return <>
      <h5 className="text-center">Friends that fully watched a common film series</h5>
      <div className="my-4 d-flex justify-content-center">
        <ListCard list={list} />
      </div>
    </>;
  }

  const getCastTab = () => {
    return <>
      <h5 className="text-center">Cast that worked in their home country</h5>
    </>;
  }

  const getAwardsTab = () => {
    return <>
      <h5 className="text-center">TOP 5 most awarded genres</h5>
      <div className="my-4 d-flex justify-content-center">
        <ListCard list={list} />
      </div>
      <h5 className="text-center">TOP 5 most awarded workers</h5>
      <div className="my-4 d-flex justify-content-center">
        <ListCard list={list} />
      </div>
      <h5 className="text-center">TOP 5 countries with the most awarded workers</h5>
      <div className="my-4 d-flex justify-content-center">
        <ListCard list={list} />
      </div>
    </>;
  }

  const displayTab = () => {
    switch (tab) {
      case 'friends':
        return getFriendsTab();
      case 'cast':
        return getCastTab();
      case 'awards':
        return getAwardsTab();
    }
  }

  return (
    <div>
      <h1 className="text-center m-5">Analytics</h1>

      <Nav justify variant="tabs" className="mb-3" activeKey={tab} onSelect={(k) => setTab(k)} >
        <Nav.Item>
          <Nav.Link className="fs-4" eventKey="friends">Friends</Nav.Link>
        </Nav.Item>
        <Nav.Item>
          <Nav.Link className="fs-4" eventKey="cast">Cast</Nav.Link>
        </Nav.Item>
        <Nav.Item>
          <Nav.Link className="fs-4" eventKey="awards">Awards</Nav.Link>
        </Nav.Item>
      </Nav>

      {displayTab()}
    </div>
  );
}

export default Analytics;
