import React from "react";
import Container from "react-bootstrap/Container";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import NavDropdown from "react-bootstrap/NavDropdown";

function NavBar() {
  return (
    <Navbar
      collapseOnSelect
      expand="sm"
      bg="darkblue"
      variant="dark"
      sticky="top"
    >
      <Container fluid>
        <Navbar.Brand href="/">
          <img
            src="/assets/logo.svg"
            className="app-logo"
            alt="filmfriend logo"
          />
        </Navbar.Brand>

        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
        <Navbar.Collapse id="responsive-navbar-nav">
          <Nav className="ms-auto">
            <Nav.Link href="/">login</Nav.Link>
            <Nav.Link href="/analytics">analytics</Nav.Link>
            <Nav.Link href="/movies">movies</Nav.Link>

            <NavDropdown title="Dropdown" id="collasible-nav-dropdown">
              <NavDropdown.Item href="/profile">profile</NavDropdown.Item>
              <NavDropdown.Item href="/recommendations">
                recommendations
              </NavDropdown.Item>
              <NavDropdown.Divider />
              <NavDropdown.Item href="/logout">logout</NavDropdown.Item>
            </NavDropdown>
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

export default NavBar;
