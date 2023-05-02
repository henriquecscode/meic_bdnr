import React from "react";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";

export default function SearchForm() {
  return (
    <Form>
      <Form.Group className="mb-3">
        <Form.Label>Title</Form.Label>
        <Form.Control type="text" placeholder="Enter title" />
      </Form.Group>

      <Form.Group className="mb-3">
        <Form.Label>Year</Form.Label>
        <Form.Control type="text" placeholder="Enter Year" />
      </Form.Group>

      <Form.Group className="mb-3" controlId="formBasicCheckbox">
        <Form.Check type="checkbox" label="Check me out" />
      </Form.Group>

      <Button className="text-center" variant="primary" type="submit">
        Search
      </Button>
    </Form>
  );
}
