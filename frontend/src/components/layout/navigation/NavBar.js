import React from "react";
import Container from "react-bootstrap/Container";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import NavDropdown from "react-bootstrap/NavDropdown";
import Image from "react-bootstrap/Image";
import logo from "./../../../assets/logo-darkblue.png";
import thumbnail from "./../../../assets/thumbnail.png";

function NavBar({ username }) {
  const UserThumbnail = (
    <Image
      src={thumbnail}
      height={25}
      width={25}
      className="thumbnail-image"
      alt="profile image"
    />
  );

  return (
    <Navbar
      collapseOnSelect
      expand="sm"
      bg="darkblue"
      variant="dark"
      sticky="top"
    >
      <Container>
        <Navbar.Brand href={`/?username=${username}`}>
          <Image src={logo} height={30} alt="filmfriend logo" />
        </Navbar.Brand>

        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
        <Navbar.Collapse id="responsive-navbar-nav">
          <Nav className="ms-auto">
            {/* 
              TODO: if not logged in, show login and signin
              <Nav.Link href={`/login?username=${username}`}>login</Nav.Link>
              <Nav.Link href={`/signin?username=${username}`}>signin</Nav.Link>
            */}
            <Nav.Link href={`/analytics?username=${username}`}>
              Analytics
            </Nav.Link>
            <Nav.Link href={`/movies?username=${username}`}>Movies</Nav.Link>

            <NavDropdown
              id="collasible-nav-dropdown"
              title={UserThumbnail}
              align="end"
            >
              <NavDropdown.ItemText>
                Logged in as <b>{username}</b>
              </NavDropdown.ItemText>
              <NavDropdown.Divider />
              <NavDropdown.Item href={`/profile?username=${username}`}>
                My Profile
              </NavDropdown.Item>
              <NavDropdown.Item href={`/recommendations?username=${username}`}>
                Recommend Me
              </NavDropdown.Item>
              {/*
                TODO: when implementing authentication 
                <NavDropdown.Divider />
                <NavDropdown.Item href={`/logout?username=${username}`}>Log out</NavDropdown.Item>
              */}
            </NavDropdown>
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

export default NavBar;
