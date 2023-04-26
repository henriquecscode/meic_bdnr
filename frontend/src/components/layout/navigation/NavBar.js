import React from "react";
import Container from "react-bootstrap/Container";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import NavDropdown from "react-bootstrap/NavDropdown";
import Image from "react-bootstrap/Image";
import logo from "./../../../assets/logo-darkblue.png";
import thumbnail from "./../../../assets/thumbnail.png";

function NavBar() {
  const username = "Fernando";

  const UserMenu = (
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
        <Navbar.Brand href="/">
          <Image src={logo} height={30} alt="filmfriend logo" />
        </Navbar.Brand>

        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
        <Navbar.Collapse id="responsive-navbar-nav">
          <Nav className="ms-auto">
            {/* 
              TODO: if not logged in, show login and signin
              <Nav.Link href="/login">login</Nav.Link>
              <Nav.Link href="/signin">signin</Nav.Link>
            */}
            <Nav.Link href="/analytics">Analytics</Nav.Link>
            <Nav.Link href="/movies">Movies</Nav.Link>

            <NavDropdown
              id="collasible-nav-dropdown"
              title={UserMenu}
              align="end"
            >
              <NavDropdown.ItemText>
                Logged in as <b>{username}</b>
              </NavDropdown.ItemText>
              <NavDropdown.Divider />
              <NavDropdown.Item href="/profile">My Profile</NavDropdown.Item>
              <NavDropdown.Item href="/recommendations">
                Recommend Me
              </NavDropdown.Item>
              <NavDropdown.Divider />
              <NavDropdown.Item href="/logout">Log out</NavDropdown.Item>
            </NavDropdown>
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

export default NavBar;
