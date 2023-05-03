import React from "react";
import Container from "react-bootstrap/Container";
import FilmCard from "../../components/cards/FilmCard";
import SearchForm from "../../components/layout/SearchForm";

function Movies({ username }) {
  const movies = [
    { name: "Avatar", link: "/movies/1" },
    { name: "Avatar: Way of Water" },
  ];

  return (
    <div>
      <h1 className="text-center m-5">Movies</h1>
      <Container>
        <div className="d-flex bd-highlight">
          <div className="p-2 flex-shrink-1 bd-highlight">
            <h5>Search</h5>
            <SearchForm />
          </div>
          <div className="p-2 w-100 bd-highlight">
            <h5>Results</h5>
            <div className="d-flex flex-column bd-highlight mb-3">
              {movies.map((m, index) => (
                <FilmCard key={index} film={m} username={username} />
              ))}
            </div>
          </div>
        </div>
      </Container>
    </div>
  );
}

export default Movies;
