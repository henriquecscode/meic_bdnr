import React, { useEffect, useState } from "react";
import Container from "react-bootstrap/Container";
import Form from "react-bootstrap/Form";
import FilmCard from "../../components/cards/FilmCard";
import MoviesAPI from "../../api/MoviesAPI";

function Movies({ username }) {
  const [movies, setMovies] = useState([]);
  const [title, setTitle] = useState("");

  useEffect(() => {
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
      }, { "title": "" }
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
      }, { "title": title }
    );
  }

  const searchForm = () => {
    return (
      <Form>
        <Form.Group className="mb-3">
          <Form.Label>Title</Form.Label>
          <Form.Control type="text" placeholder="Enter title" value={title} onChange={(e) => { setTitle(e.target.value); submitForm() }} />
        </Form.Group>

        {/* <Form.Group className="mb-3">
          <Form.Label>Year</Form.Label>
          <Form.Control type="text" placeholder="Enter Year" />
        </Form.Group>

        <Form.Group className="mb-3" controlId="formBasicCheckbox">
          <Form.Check type="checkbox" label="Check me out" />
        </Form.Group> */}
      </Form>
    );
  }

  // TODO: Add text search
  return (
    <div>
      <h1 className="text-center m-5">Movies</h1>
      <Container>
        <div className="d-flex bd-highlight">
          <div className="p-2 flex-shrink-1 bd-highlight">
            <h5>Search</h5>
            {searchForm()}
          </div>
          <div className="p-2 w-100 bd-highlight">
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
