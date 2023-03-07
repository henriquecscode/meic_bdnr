# Orient DB

## Basic Java Application

Application done by following the technology [official documentation](https://orientdb.org/docs/3.0.x/fiveminute/) with the objective to learn and apply the OrientDB basics operations using the available Java API.

### Context

It is a very simple application with two classes:
- **Person**: a vertex type containing information about people
- **FriendOf**: an edge class that connects people together

### Code

> The application was developing using IntelliJ Idea, but any other IDE can be used as long as it supports **Java 8** and **Maven**.

The application code ilustrate the next steps:
- connection to the server
- connection to the database (created before hand: name=test, user=admin, pass=admin)
- creation of the schema
- creation of the graph: vertices and edges 
- querying the database
