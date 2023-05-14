import React, { useEffect, useState } from "react";
import Container from "react-bootstrap/Container";
import Form from "react-bootstrap/Form";
import FilmCard from "../../components/cards/FilmCard";
import MoviesAPI from "../../api/MoviesAPI";

function Movies({ username }) {
  const [movies, setMovies] = useState([]);
  const [genres, setGenres] = useState([]);
  const [title, setTitle] = useState("");
  const [genre, setGenre] = useState(null);
  const [year, setYear] = useState(null);

  useEffect(() => {
    document.title = "FilmFriend - Movies Search";

    const api = new MoviesAPI();

    api.getGenres(
      (json) => {
        setGenres(
          json
            .filter(
              (genre) => genre.name !== "" && genre.name.toLowerCase() !== "\\n"
            )
            .map((genre) => genre.name)
        );
      },
      (error) => {
        setMovies([]);
        console.log(error);
      }
    );

    api.getSearch(
      (json) => {
        if (json && Array.isArray(json) && json.length > 0) {
          setMovies(json);
        } else {
          setMovies([]);
        }
      },
      (error) => {
        setMovies([]);
        console.log(error);
      },
      { title: "" }
    );
  }, []);

  const submitForm = () => {
    const api = new MoviesAPI();

    api.getSearch(
      (json) => {
        if (json && Array.isArray(json) && json.length > 0) {
          setMovies(json);
        } else {
          setMovies([]);
        }
      },
      (error) => {
        setMovies([]);
        console.log(error);
      },
      { title: title, genre: genre, year: year }
    );
  };

  const searchForm = () => {
    let genreOptions = [];

    genres.forEach((genre, index) => {
      genreOptions.push(
        <option key={index + 1} value={genre}>
          {genre}
        </option>
      );
    });

    return (
      <Form>
        <Form.Group className="mb-3">
          <Form.Label className="mt-3">Title</Form.Label>
          <Form.Control
            type="text"
            placeholder="Enter title"
            value={title}
            onChange={(e) => {
              setTitle(e.target.value);
              submitForm();
            }}
          />

          <Form.Label className="mt-3">Genres</Form.Label>
          <Form.Select
            aria-label="Genre"
            onChange={(e) => {
              if (e.target.value === "All Genres") {
                setGenre(null);
              } else {
                setGenre(e.target.value);
              }
              submitForm();
            }}
          >
            <option value={null}>All Genres</option>
            {genreOptions}
          </Form.Select>

          <Form.Label className="mt-3">Year</Form.Label>
          <Form.Control
            type="number"
            min={1900}
            max={2025}
            placeholder="Enter Year"
            onChange={(e) => {
              if (e.target.value === "") {
                setYear(null);
              } else {
                setYear(e.target.value);
              }
            }}
            onBlur={() => {
              // TODO: better if in every change?
              submitForm();
            }}
          />

          {/* // TODO ? */}
          {/*<Form.Check className="mt-3" type="checkbox" label="With Awards?" /> */}
        </Form.Group>
      </Form>
    );
  };

  // TODO: Add text search
  return (
    <div>
      <Container className="py-5">
        <div className="d-flex bd-highlight">
          <div className="pe-3 flex-shrink-1 bd-highlight">
            <h5>Search</h5>
            {searchForm()}
          </div>
          <div className="ps-4 w-100 bd-highlight border-start">
            <h5>Results</h5>
            <div className="d-flex flex-column bd-highlight mb-3">
              {movies.map((m) => (
                <FilmCard key={m.tid} film={m} username={username} />
              ))}
            </div>
          </div>
        </div>
      </Container>
    </div>
  );
}

export default Movies;
