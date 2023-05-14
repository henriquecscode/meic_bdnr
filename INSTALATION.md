# FilmFriend

### Arquitecture

The orientDb (database) and the application's frontend (react application) will be running in docker containers.

Unfortunately we couldn't get the backend (java springboot - maven project) running in a docker container. Despite all the containers being on the same network we couldn't successfully get the 3 to communicate with each other. At the limit, we managed to get the java backend to connect to the database, but in this configuration it refused all connection requests from the frontend.

To connect the frontend to the local backend we use a proxy that redirects the requests to the correct service.

### Prerequisites

To run the application will need to have installed in our machines the following software:

- docker
- maven
- java

### Setup and Running

First make sure that docker is running and open a terminal in the root folder of the project and run the following commands:

```cmd
docker compose build
docker compose run
```

After this commands you should see a composer with 3 different containers running: database, frontend and proxy services.

Second, now that the database is reachable (this can be checked by accessing the `http://localhost:2480`), in a terminal in the root folder perform the next commands:

```cmd
cd backend
.\scripts\compile.bat
.\scripts\load.bat
.\scripts\run.bat
```

**NOTE**: it is important to verify that the end of line of each `.sh` or `.bat` file is `LF`.

You should have a backend running and a populated database in the orientDB's container.

To check if the data was loaded correctly, in a navigator of your choice (eg.: google Chrome) access `http://localhost:2480` (OrientDB Studio) and connect to the database `app_sample` (the one selected by default) with the user `root` and password `root` and investigate the schema, vertexes and edges to verify that everything was loaded as expected.

The next time you just what to start the backend without reloading the data simple run only the third command presented.

### Application

The application is available in the `http://localhost:3000`.

It is possible to check a user's profile and recommendations, as well as search and filter movies and see it's details. Futhermore, we have access to some analytics regarding the data.

To change the information according to a user's point of view just add to the url a query param in the format `?username=<username>`. The list of all usernames in the system can be accessed from the navbar in the `(Users)` tab.

Some other features involve add and remove friends (in the user's profile); add and remove movies from the watchlist (in the recommendations tab and user's profile); add, edit and remove a comment that a user name in a movie (in the page of the details of a movie which can be access by clicking `see details` or title of a film in its cards).
